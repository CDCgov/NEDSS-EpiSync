from behave import *

from faker import Faker
from faker.providers import BaseProvider
import random
import csv
from datetime import datetime
from dateutil import relativedelta
import uuid
import os.path

def get_name():
    return fake.name()

def get_first_name():
    return fake.first_name()

def get_middle_name():
    return fake.middle_name()

def get_last_name():
    return fake.last_name()

def get_suffix():
    return fake.random_element(elements= ('ESQ', 'II', 'III', 'IV','JR','SR','V'))

def get_date_submitted():
    return fake.date_between(start_date='today', end_date='today').strftime('%d/%m/%Y')
def get_birth_date():
    return fake.date_of_birth(minimum_age=0, maximum_age=99)

def get_reported_age(dob):
    today_date = datetime.now()
    delta = relativedelta.relativedelta(today_date, dob)
    if(delta.years == 0 & delta.months == 0):
        return f'{delta.days} : days'
    elif(delta.years == 0):
        return f'{delta.months} : Months'
    else:
        return f'{delta.years} : Years'

def get_reported_age_units():
    # return fake.random_element(elements=('years','months'))
    return 'years'

def get_random_id():
    # return set(fake.unique.random_int() for i in range(1000000))
    return uuid.uuid4()


def get_gender():
    return fake.random_element(elements=('M', 'F','U'))

def get_marital_status():
    return fake.random_element(elements=('A', 'B','C','D', 'E', 'F', 'G', 'I', 'L', 'M', 'O', 'P', 'R', 'S', 'T', 'U','W'))

def get_race():
    return fake.random_element(elements=('White', 'Black', 'Asian', 'Hispanic', 'Native American'))

def get_race_code():
    return fake.random_element(elements=('1002-5','2028-9','2054-5','2076-8','2106-3','2131-1','ASKU','NASK','NI','PHC1175','U'))


def get_ethnic_group():
    return fake.random_element(elements=('African American', 'Asian American', 'Hispanic', 'Native American', 'Pacific Islander', 'White'))

def get_ethnic_grp():
    return fake.random_element(elements=('2135-2','2186-5','UNK'))

def get_national_reporting_jurisdiction():
    return fake.number_between(minimum=0, maximum=80, excluding=[3,52,57,58,59,61,62,63,67,7,71,73,74,75,75,77])

def get_birth_country():
    return fake.country()


def get_other_birth_country():
    return fake.country()

def get_country_of_exposure():
    return fake.country()

def get_residence_country():
    return fake.random_element(elements=('100', '104', '108', '112', '116','12','120','124','132','136','140,''144','148','152','156','158','16','170','174','178','180','184','188','191','192','196','20','204','208','212','214','218,''222','226','231','232','233','234','238','24','242','246','250','254','258','262','266','268','270','275','276','28','288,''292','296','300','304','308','31','312','316','32','320','324','328','332','336','340','344','348','352','356','36','360,''364','368','372','376','380','384','388','392','398','4','40','400','404','408','410','414','418','422','426','428','430,''434','438','44','440','442','446','450','454','458','462','466','470','474','478','48','480','484','492','496','498','50,''500','504','508','51','512','516','52','520','524','528','530','533','540','548','554','558','56','562','566','570','574,''578','580','583','584','585','586','591','598','60','600','604','608','612','616','620','624','626','630','634','638','64,''642','643','646','654','659','660','662','666','670','674','678','68','682','686','690','694','70','702','703','704','705,''706','710','716','72','724','732','736','740','744','748','752','756','76','760','762','764','768','772','776','780','784,''788','792','795','796','798','8','800','804','807','818','826','830','833','834','84','840','850','854','858','860','862,''876','882','887','891','894','90','92','96','ALA','AT','ATF','BLM','BQ','BS','BVT','CIV','CR','CSHH','CXR','DQ','EU','FQ','GGY,''GO','GZ','HMD','HQ','IOT','IP','JEY','JN','JQ','JU','KQ','LAO','LQ','MAF','MIUM','MNE','MYT','NI','NTHH','PF','PG','PUUM','REU,''SGS','SRB','SUHH','TE','TPTL','UMI','UNK','WKUM','YUCS','ZRCD'))

def get_birth_country():
    return fake.country()


def get_county():
    # return fake.county()
    ''


def get_state():
    return fake.state()


def get_address():
    return fake.address()


def get_zipcode():
    return fake.zipcode()

def get_city():
    return fake.city()

def get_phone_number():
    return fake.phone_number()

def get_email():
    return fake.email()

def get_comment():
    return fake.text(max_nb_chars=15)

def get_ynu():
    return fake.random_element(elements=('N', 'UNK', 'Y'))
def get_pregnency_status():
    return fake.random_element(elements=('Yes', 'No','Unknown'))

def get_jurisdiction_code():
    return fake.random_element(elements=('130006','130004','130005','130001','130002','999999'))

def get_diagnosis_date(start_date_value, end_date_value):
    return fake.date_between(start_date=start_date_value, end_date=end_date_value)

def get_hospitalized():
    return fake.random_element(elements=('Yes', 'No', 'Unknown'))

def get_subject_died():
    return fake.random_element(elements=('Yes', 'No', 'Unknown'))

def get_illness_onset(start_date_value):
    return fake.date_between(start_date=start_date_value, end_date='today')

def get_illness_end(start_date_value):
    return fake.date_between(start_date=start_date_value, end_date='today')

def get_admission_date(start_date_value):
    return fake.date_between(start_date=start_date_value, end_date='today')

def get_discharge_date(start_date_value):
    return fake.date_between(start_date=start_date_value, end_date='today')

# Create a list of tuples containing data for each column
def generate_data():
    dob = get_birth_date()
    age_tmp = get_reported_age(dob)
    age = age_tmp.split(" : ")[0]
    age_unit = age_tmp.split(" : ")[1]
    column_data = [
        ('identifier', 'id' , 'answer'),
        ('NBS104', 1130085, get_date_submitted()),
        ('DEM196', 1130086, get_comment()),
        ('NBS095', 1130088, get_date_submitted()),
        ('DEM104', 1130089, get_first_name()),
        # ('DEM105', 1130090, get_middle_name()),
        ('DEM102', 1130091, get_last_name()),
        ('DEM107 ', 1130092, get_suffix()),
        ('DEM115', 1130095, get_birth_date()),
        ('INV2001', 1130096, age),
        ('INV2002', 1130097, age_unit),
        ('DEM126', 1130098, get_birth_country()),
        ('DEM113', 1130099, get_gender()),
        ('NBS097', 1130100, get_date_submitted()),
        ('DEM127', 1130101, get_ynu()),
        ('DEM128', 1130102, get_date_submitted()), #decesed date
        ('DEM140', 1130104, get_marital_status()),
        # ('DEM159', 1130107, #street address 1),
        # ('DEM160',1130108, #street address 2 ),
        ('DEM161', 1130109, get_city()),
        # ('DEM162', 1130110, get_national_reporting_jurisdiction()),
        ('DEM163', 1130111, get_zipcode()),
        ('DEM165', 1130112, get_county()),
        ('DEM167', 1130113, get_residence_country()),
        ('DEM177', 1130116, get_phone_number()),
        ('DEM182', 1130120, get_email()),
        ('DEM155', 1130123, get_ethnic_grp()),
        ('NBS101', 1130124, get_date_submitted()), #raceinfoasofdate
        ('DEM152', 1130125, get_race_code()),
         ('INV107', 1130129, get_jurisdiction_code()),
         ('NOT103', 1129983, get_date_submitted()),
        ('NBS052', 1129986, get_state()),
        ('INV190', 1129991, get_name()),
        # ('NOT116', 1129994, get_national_reporting_jurisdiction()),
        ('NBS031', 1129995, get_city()),
        ('INV103', 1129996, get_phone_number()),
        ('INV118', 1129999, get_zipcode()),
        ('NBS037', 1130000, get_address()),
        ('INV102', 1130005, get_email()),
        ('NBS038', 1130012, get_city()),
        ('NBS034', 1130020, get_email()),
        ('INV100', 1130021, get_last_name()),
        ('INV125', 1130025, get_first_name()),
        ('INV105', 1130031, get_last_name()),
        ('NOT106', 1130033, get_date_submitted()),
        ('NBS048', 1130034, get_suffix()),
        ('NBS094', 1130039, dob),
        ('NBS053', 1130041, get_zipcode()),
        ('INV116', 1130050, get_city()),
        # ('INV169', 1130051, get_condition_code),
        # ('NBS032', 1130060, get_national_reporting_jurisdiction()),
        # ('INV117', 1130063, get_national_reporting_jurisdiction()),
        ('INV2001', 1090964, dob),
        ('INV2001', 1090964, age),
        ('INV2002', 1090939, age_unit),
        ('INV502', 1090983, get_country_of_exposure()),
        ('INV503', 1091090, get_state()),
    ]
    return column_data

fake = Faker()

# Write the transposed data to a CSV file

@given(u'we synthesize test data to write csv file for investigation')
def step_impl(context):
    file_name = "/docker_vol_shared/datagen.csv"
    if os.path.exists("/docker_vol_shared"):
        file_name = "/docker_vol_shared/datagen.csv"
    else:
        file_name = 'docker_vol_shared/datagen.csv'
    transposed_data = generate_data()
    with open(file_name, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for row in transposed_data:
            writer.writerow(row)