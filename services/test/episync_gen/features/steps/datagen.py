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
    return fake.random_element(elements=('Male', 'Female'))


def get_race():
    return fake.random_element(elements=('White', 'Black', 'Asian', 'Hispanic', 'Native American'))


def get_ethnic_group():
    return fake.random_element(elements=('African American', 'Asian American', 'Hispanic', 'Native American', 'Pacific Islander', 'White'))


def get_birth_country():
    return fake.country()


def get_other_birth_country():
    return fake.country()

def get_country_of_exposure():
    return fake.country()

def get_residence_country():
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

def get_pregnency_status():
    return fake.random_element(elements=('Yes', 'No','Unknown'))

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