from behave import *

from faker import Faker
from faker.providers import BaseProvider
import random
import csv
from datetime import datetime
import uuid
import os.path
import time

def capitalize(str):
    return str.capitalize()

def get_name():
    return fake.name()

def get_birth_date():
    # return datetime.strftime(fake.date_time_this_decade(), "%B %d, %Y")
    return fake.date_of_birth(minimum_age=18, maximum_age=99)


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

def generate_data():
    birth_date = get_birth_date()
    # illness_onset Date between birth date and today
    illness_onset = get_illness_onset(birth_date)
    # illness_end between illness_onset and today
    illness_end = get_illness_end(illness_onset)
    # diagnosis_date between illness_onset and illness_end
    diagnosis_date = get_diagnosis_date(illness_onset, illness_end)
    admission_date = get_admission_date(illness_onset)
    discharge_date = get_discharge_date(admission_date)

    return [get_random_id(),
            get_random_id(),
            get_name(),
            birth_date,
            get_gender(),
            get_race(),
            'other',
            get_ethnic_group(),
            get_birth_country(),
            get_other_birth_country(),
            get_residence_country(),
            get_county(),
            get_state(),
            get_zipcode(),
            illness_onset,
            illness_end,
            get_pregnency_status(),
            diagnosis_date,
            get_hospitalized(),
            admission_date,
            discharge_date,
            get_subject_died(),
            # get_deceased_date()
            ]

fake = Faker()

@given(u'we synthesize test data to write csv file')
def step_impl(context):
    file_name = "/docker_vol_shared/episync.csv"
    if os.path.exists("/docker_vol_shared"):
        file_name = "/docker_vol_shared/episync.csv"
    else:
        file_name = 'docker_vol_shared/episync.csv'

    with open(file_name, 'w') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['episync_mmg_message_profile_identifier',
                        'episync_mmg_local_subject_id',
                        'episync_mmg_name',
                        'episync_mmg_birth_date',
                        'episync_mmg_sex',
                        'episync_mmg_race',
                        'episync_mmg_other_race',
                        'episync_mmg_ethnic_group',
                        'episync_mmg_birth_country',
                        'episync_mmg_other_birth_place',
                        'episync_mmg_country_of_residence',
                        'episync_mmg_subject_address_county',
                        'episync_mmg_subject_address_state',
                        'episync_mmg_subject_address_zip',
                        'episync_mmg_date_of_illness_onset',
                        'episync_mmg_illness_end_date',
                        'episync_mmg_pregnancy_status',
                        'episync_mmg_diagnosis_date',
                        'episync_mmg_hospitalized',
                        'episync_mmg_admission_date',
                        'episync_mmg_discharge_date',
                        'episync_mmg_subject_died',
                        'episync_mmg_deceased_date',
                        ])

        for n in range(1, 100):
            writer.writerow(generate_data())