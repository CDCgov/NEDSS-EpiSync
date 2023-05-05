"""
cli.py - EpiSync CLI command tool
"""

import configparser
import getpass
import json
import logging
import os
import platform
import sys
import warnings
from pathlib import Path

import click
import pandas as pd
import requests
from fastapi import FastAPI, File, UploadFile
from sqlalchemy import create_engine
from sqlalchemy.orm import Session
from sqlalchemy.sql import text

USE_FK = False
HOSTNAME = platform.node()

current_user = getpass.getuser()

CONFIG = configparser.ConfigParser()

HOME = str(Path.home())
ini = HOME + "/episync.ini"
CONFIG.read(ini)

INIPATHS = [".", HOME]

sqltypes = {
    "id": "text",
    "date": "date",
    "gender": "text",
    "race": "text",
    "ethnicity": "text",
    "country": "text",
    "number": "int",
    "age": "int",
    "duration": "int",
}


@click.group(invoke_without_command=True)
@click.option("--debug", is_flag=True, default=False, help="Debug switch")
@click.option(
    "-i",
    "--ini",
    default=None,
    help="EpiSync .ini configuration file path",
)
@click.pass_context
def cli(context, debug, ini):
    for handler in logging.root.handlers[:]:
        logging.root.removeHandler(handler)

    if debug:
        logging.basicConfig(
            format="%(asctime)s : %(name)s %(levelname)s : %(message)s",
            level=logging.DEBUG,
        )
    else:
        logging.basicConfig(
            format="%(asctime)s : %(name)s %(levelname)s : %(message)s",
            level=logging.ERROR,
        )
        logger = logging.getLogger("sqlalchemy.engine.Engine")
        logger.setLevel(logging.ERROR)  # modifies the current log handler
        logger.addHandler(logging.NullHandler())  # creates an additional log handler

    if len(sys.argv) == 1:
        click.echo(context.get_help())

    if not ini:
        for inipath in INIPATHS:
            if os.path.exists(inipath + "/episync.ini"):
                CONFIG.read(inipath + "/episync.ini")
    else:
        CONFIG.read(ini)

    db = CONFIG.get("database", "uri")
    logging.info("database %s", db)
    engine = create_engine(db, echo=False, isolation_level="REPEATABLE READ")
    session = Session(engine)
    context.obj = {"session": session, "engine": engine}
    raw = engine.raw_connection()
    context.obj["raw"] = raw
    context.obj["connect"] = engine.connect()


@cli.group()
def ddl():
    """Commands to create and manage EpiSync Data Dictionary"""
    pass


@cli.group()
def publish():
    """Commands to publish EpiSync CSV data"""
    pass


@cli.group()
def validate():
    """Commands to validate EpiSync CSV data"""
    pass


@cli.group()
def test():
    """Commands to test EpiSync"""
    pass


@cli.group()
def generate():
    """Commands to generate EpiSync CSV data"""
    pass


@cli.group()
def api():
    """EpiSync API server commands"""
    pass


def get_edd_json(session):
    with warnings.catch_warnings():
        warnings.simplefilter("ignore")

        with session:
            dd_rows = session.execute(
                "select col, name, description,type, rule, cardinality  from episync_dd"
            )

            row_list = list(dd_rows)
            return [
                {
                    "col": row[0].strip() if row[0] else "",
                    "name": row[1].strip() if row[1] else "",
                    "type": row[3].strip() if row[3] else "",
                    "rule": row[4].strip() if row[4] else "",
                    "cardinality": row[5].strip() if row[5] else "",
                    "description": row[2].strip() if row[2] else "",
                }
                for row in row_list
            ]


@api.command()
@click.pass_context
def start(context):
    """Start EpiSync Data Dictionary Server"""
    import uvicorn
    from pydantic import BaseModel

    engine = context.obj["engine"]
    app = FastAPI(
        title="EpiSync",
        description="EpiSync Data Dictionary API",
        version="0.0.1",
    )

    class EpiSyncValidation(BaseModel):
        validates: bool
        message: str

    class EpiSyncDataField(BaseModel):
        col: str
        name: str
        rule: str
        type: str
        cardinality: str
        description: str

    @app.get("/dictionary/", response_model=list[EpiSyncDataField], tags=["dictionary"])
    async def dictionary():
        return get_edd_json(context.obj["session"])

    @app.post("/validate/", response_model=EpiSyncValidation, tags=["validate"])
    async def validate(file: UploadFile = File(...)) -> EpiSyncValidation:
        df = pd.read_csv(file.file)
        print(df.iloc[[0]])

        print("DF", df)
        db = CONFIG.get("database", "uri")
        print(db)
        engine = create_engine(db, echo=True, isolation_level="REPEATABLE READ")

        with warnings.catch_warnings():
            warnings.simplefilter("ignore")
            try:
                df.to_sql(
                    "episync_mmg",
                    con=context.obj["connect"],
                    if_exists="append",
                    index=False,
                )

                response = EpiSyncValidation(
                    validates=True, message="All rows validate"
                )
            except Exception as ex:
                print(ex)
                response = EpiSyncValidation(validates=False, message=str(ex))

        return response

    uvicorn.run(app, host="0.0.0.0", port=8014)


@ddl.command(name="show")
@click.option(
    "-j",
    "--json",
    "jsonformat",
    is_flag=True,
    default=False,
    help="Dump Data Dictionary to JSON",
)
@click.option(
    "-d", "--desc", is_flag=True, default=False, help="Add the field description"
)
@click.pass_context
def show_ddl(context, jsonformat, desc):
    """Show the current EpiSync DDL"""
    from prettytable import PrettyTable

    x = PrettyTable()

    cols = ["Column", "Name"]
    if desc:
        cols.append("Description")
    cols += ["Type", "Rule", "Cardinality"]

    x.field_names = cols
    x._max_width = {"Name": 30, "Column": 30, "Rule": 20, "Description": 120}

    db = CONFIG.get("database", "uri")

    engine = create_engine(db, echo=True, isolation_level="REPEATABLE READ")

    with warnings.catch_warnings():
        warnings.simplefilter("ignore")

        with context.obj["session"] as session:
            dd_rows = session.execute(
                text(
                    "select col, name, description,type, rule, cardinality  from episync_dd"
                )
            )

            row_list = list(dd_rows)
            for row in row_list:
                if not desc:
                    _row = [row[0], row[1], row[3], row[4], row[5]]
                else:
                    _row = row
                x.add_row(_row)

            if not jsonformat:
                print(x)
            else:
                print(
                    json.dumps(
                        [
                            {
                                "col": row[0].strip() if row[0] else "",
                                "name": row[1].strip() if row[1] else "",
                                "type": row[3].strip() if row[3] else "",
                                "rule": row[4].strip() if row[4] else "",
                                "cardinality": row[5].strip() if row[5] else "",
                                "description": row[2].strip() if row[2] else "",
                            }
                            for row in row_list
                        ],
                        indent=4,
                    )
                )


@ddl.command(name="create")
@click.pass_context
def create_ddl(context):
    """Create the EpiSync DDL Data Dictionary"""
    db = CONFIG.get("database", "uri")

    engine = create_engine(db, echo=True, isolation_level="REPEATABLE READ")

    with warnings.catch_warnings():
        warnings.simplefilter("ignore")

        with context.obj["session"] as session:
            mmg_dd = CONFIG.get("mmg", "file")
            mmg_des = pd.read_excel(mmg_dd, sheet_name="Data Elements")

            r = requests.get(
                "https://phinvads.cdc.gov/vads/DownloadHotTopicDetailFile.action?filename=29DF7191-76CC-E611-8E51-0017A477041A",
                allow_redirects=True,
            )
            open("2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx", "wb").write(
                r.content
            )

            phinvads_race = pd.read_excel(
                "2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx",
                sheet_name="EthnicityMapping2022To2000",
            )

            race_codes_2022 = [
                code
                for code in phinvads_race["ConceptCode2022"].tolist()
                if isinstance(code, str)
            ]
            race_codes_2000 = phinvads_race["ConceptCodeEthnicity2000"].tolist()
            race_code_names_2022 = phinvads_race["Concept-Ethnicity"].tolist()
            race_code_names_2000 = phinvads_race["CenceptEthnicity2000"]

            codes_2000 = [
                {"code": code, "name": name}
                for code, name in zip(race_codes_2000, race_code_names_2000)
                if isinstance(code, str)
            ]
            codes_2022 = [
                {"code": code, "name": name}
                for code, name in zip(race_codes_2022, race_code_names_2022)
                if isinstance(code, str)
            ]

            epi_de_cols = mmg_des["EpiSync DE Name"].tolist()
            epi_de_types = mmg_des["EpiSync DE Type"].tolist()
            epi_de_rules = mmg_des["EpiSync DE Constraint"].tolist()
            epi_de_desc = mmg_des["Data Element Description"].tolist()
            epi_de_card = mmg_des["May Repeat"].tolist()
            epi_de_names = mmg_des["Data Element (DE) Name"].tolist()

            session.execute(
                text("CREATE TABLE phinvads_ethnicity (code text , name text)")
            )
            session.commit()
            print("Created phinvads_ethnicity table.")
            for code_2000, code_2022 in zip(codes_2000, codes_2022):
                code_2000df = pd.DataFrame([code_2000])
                code_2022df = pd.DataFrame([code_2022])
                try:
                    print(code_2000df, code_2022df)
                    code_2000df.to_sql(
                        "phinvads_ethnicity",
                        con=context.obj["connect"],
                        if_exists="append",
                        index=False,
                    )
                    code_2022df.to_sql(
                        "phinvads_ethnicity",
                        con=context.obj["connect"],
                        if_exists="append",
                        index=False,
                    )
                    print("Done")
                except Exception as ex:
                    print(ex)

            # Create a SQL table to hold the episync data dictionary + validation rules
            table_string = "CREATE TABLE episync_mmg ("

            epi_col_names = []

            print("Creating episync_dd table")
            session.execute(
                text(
                    "CREATE table episync_dd (col text, type text, rule text, description text, cardinality text, name text)"
                )
            )

            session.commit()
            print("Created episync_dd table.")
            for col, type, rule, description, cardinality, name in zip(
                epi_de_cols,
                epi_de_types,
                epi_de_rules,
                epi_de_desc,
                epi_de_card,
                epi_de_names,
            ):
                try:
                    float(type)
                    continue
                except:
                    pass

                metarow = {
                    "col": col,
                    "type": type,
                    "rule": rule,
                    "name": name,
                    "description": description,
                    "cardinality": cardinality,
                }
                df = pd.DataFrame([metarow])
                df.to_sql(
                    "episync_dd",
                    con=context.obj["connect"],
                    if_exists="append",
                    index=False,
                )

            fks = []
            for col, type, rule in zip(epi_de_cols, epi_de_types, epi_de_rules):
                check = " "
                try:
                    float(col)
                    continue
                except:
                    pass
                epi_col_names += [col]

                _type = "text"
                if type in sqltypes:
                    _type = sqltypes[type]

                try:
                    float(rule)

                except:
                    if rule.find("PHINVADS_RACE") > 0:
                        races = session.execute(
                            text("SELECT code FROM phinvads_ethnicity")
                        ).fetchall()
                        races = ",".join(["'" + race[0] + "'" for race in races])
                        rule = rule.replace("PHINVADS_RACE", races)

                        fkrule = f", FOREIGN KEY ({col}) REFERENCES phinvads_ethnicity(code) "
                        fks += [rule]

                    if not USE_FK:
                        check = " CHECK(" + rule + ") "

                table_string += col + " " + str(_type) + " " + check + ","

            if USE_FK:
                table_string = table_string[:-1] + " ".join(fks) + ")"
            else:
                table_string = table_string[:-1] + ")"

            logging.info("TABLE_STRING %s", table_string)
            session.execute(text(table_string))
            # session.execute("PRAGMA foreign_keys = ON")
            session.commit()
            logging.info("Created episync_mmg table.")
            # Create two sample data rows, one with a violation
            row1 = {key: None for key in epi_de_cols if str(key) != "nan"}
            row1["episync_mmg_duration_of_hospital_stay_in_days"] = 90  # type: ignore[assignment]

            row2 = {key: None for key in epi_de_cols if str(key) != "nan"}
            row2["episync_mmg_duration_of_hospital_stay_in_days"] = 100  # type: ignore[assignment]

            row2["episync_mmg_race"] = "2155-0"  # type: ignore[assignment]

            # Inject these sample rows into the SQL episync_mmg internal database
            for row in [row1, row2]:
                df = pd.DataFrame([row])
                try:
                    df.to_sql(
                        "episync_mmg",
                        con=context.obj["connect"],
                        if_exists="append",
                        index=False,
                    )
                except Exception as ex:
                    print(ex)

            # Get all the rows that passed validation constraints
            all = session.execute(text("SELECT * FROM episync_mmg")).fetchall()
