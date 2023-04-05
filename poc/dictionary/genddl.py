import pandas as pd
import sqlite3
import os
import csv
import requests
import warnings

try:
    os.remove("episync.db")
    os.remove("episync.csv")
    os.remove("2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx")
except:
    pass

sqltypes = {
    'id': 'text',
    'date': 'date',
    'gender': 'text',
    'race': 'text',
    'ethnicity': 'text',
    'country': 'text',
    'number': 'int'
}


with warnings.catch_warnings():
    warnings.simplefilter("ignore")

    # Internal validate database
    conn = sqlite3.connect('episync.db')
    c = conn.cursor()

    # Read in data dictionary, rules, types and column names
    mmg_des = pd.read_excel("~/episync-dd.xlsx", sheet_name="Data Elements")

    print(mmg_des)

    r = requests.get("https://phinvads.cdc.gov/vads/DownloadHotTopicDetailFile.action?filename=29DF7191-76CC-E611-8E51-0017A477041A", allow_redirects=True)
    open('2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx', 'wb').write(r.content)

    phinvads_race = pd.read_excel("2022_RaceAndEthnicityFinal_TablesforPub_Final.xlsx", sheet_name="EthnicityMapping2022To2000")

    race_codes_2022 = [ code for code in phinvads_race['ConceptCode2022'].tolist() if type(code) is str ]
    race_codes_2000 = phinvads_race['ConceptCodeEthnicity2000'].tolist()
    race_code_names_2022 = phinvads_race['Concept-Ethnicity'].tolist()
    race_code_names_2000 = phinvads_race['CenceptEthnicity2000']

    codes_2000 = [ {'code':code, 'name':name} for code, name in zip(race_codes_2000, race_code_names_2000) if type(code) is str]
    codes_2022 = [ {'code':code, 'name':name} for code, name in zip(race_codes_2022, race_code_names_2022) if type(code) is str]

    epi_de_cols = mmg_des['EpiSync DE Name'].tolist()
    epi_de_types = mmg_des['EpiSync DE Type'].tolist()
    epi_de_rules = mmg_des['EpiSync DE Constraint'].tolist()
    epi_de_names = mmg_des['Data Element (DE) Name'].tolist()

    ethnicity_table = 'CREATE TABLE phinvads_ethnicity (code text, name text)'

    c.execute(ethnicity_table)
    for code_2000, code_2022 in zip(codes_2000,codes_2022):
        code_2000df = pd.DataFrame([code_2000])
        code_2022df = pd.DataFrame([code_2022])
        try:
            code_2000df.to_sql('phinvads_ethnicity', conn, if_exists='append', index=False)
            code_2022df.to_sql('phinvads_ethnicity', conn, if_exists='append', index=False)
        except Exception as ex:
            print(ex)

    # Create a SQL table to hold the episync data dictionary + validation rules
    table_string = 'CREATE TABLE episync_mmg ('

    epi_col_names = []

    c.execute("CREATE table episync_dd (column text, type text, rule text, name text)")

    for col, type, rule, name in zip(epi_de_cols, epi_de_types, epi_de_rules, epi_de_names):
        
        try:
            float(type)
            continue
        except:
            pass

        metarow = {'column':col, 'type':type, 'rule':rule, 'name':name}
        df = pd.DataFrame([metarow])
        df.to_sql('episync_dd', conn, if_exists='append', index=False)

    for col, type, rule in zip(epi_de_cols, epi_de_types, epi_de_rules):
        print(col, type, rule)
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
                races = c.execute('''SELECT code FROM phinvads_ethnicity''').fetchall()
                races = ",".join(['"'+race[0]+'"' for race in races])
                rule = rule.replace('PHINVADS_RACE',races)
            check = " CHECK("+rule+") "

        table_string += col+" "+str(_type)+" "+check+","

    table_string = table_string[:-1]+")"

    # Print the table create SQL to the console
    print(table_string)

    # Create the table
    c.execute(table_string)

    # Create two sample data rows, one with a violation
    row1 = {key: None for key in epi_de_cols if str(key) != "nan"}
    row1['episync_mmg_duration_of_hospital_stay_in_days'] = 90

    row2 = {key: None for key in epi_de_cols if str(key) != "nan"}
    row2['episync_mmg_duration_of_hospital_stay_in_days'] = 100
    row2['episync_mmg_race'] = '2155-0XXX'

    # Inject these sample rows into the SQL episync_mmg internal database
    for row in [row1, row2]:
        df = pd.DataFrame([row])
        try:
            df.to_sql('episync_mmg', conn, if_exists='append', index=False)
        except Exception as ex:
            print(ex)

    # Get all the rows that passed validation constraints
    all = c.execute('''SELECT * FROM episync_mmg''').fetchall()
    print("-----------------------------------------------------------------------")
    print()
    print()

    # Create an episync csv from the rows that pass validation
    with open("episync.csv", "w") as epfile:
        csv_writer = csv.writer(epfile)
        csv_writer.writerow(epi_col_names)
        for row in all:
            csv_writer.writerow(row)

    # Print the csv we created to the console
    with open("episync.csv", "r") as epfile:
        rows = epfile.read()
        print(rows)

    conn.close()
