import requests
import json
import os
import csv

@given('I have url endpoint "{url}"')
def step_impl(context, url):
    context.url = url
    print(url)

@given('I make "{method}" call with body "{body}"')
def step_impl(context, method, body):
    if method == "GET":
        response = requests.get(context.url)
    else:
        payload = json.dumps(json.loads(body))
        headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
        response = requests.request("POST", context.url, headers=headers, data=payload)
    context.response = response

@given('the response code is "{code}"')
def step_impl(context, code):
    if(int(code) == int(context.response.status_code)):
        print("Response code is " + str(code) + " as expected..")
    else:
        exception_message="Response code not as expected. Expected: " + str(code) + " Actual: " + str(context.response.status_code)
        raise Exception(exception_message)
    # raise NotImplementedError(u'STEP: Given the reponse is 404 error')

@given('message is "{message}"')
def step_impl(context, message):
    print()
    if str(message) == str(context.response.text):
        print("Response message is " + str(message) + " as expected..")
    else:
        exception_message = "Response message not as expected. Expected: " + message + " Actual: " + context.response.text
        raise Exception(exception_message)
    # raise NotImplementedError(u'STEP: Given the reponse is 404 error')

@given(u'I capture the auth token')
def step_impl(context):
    a = json.loads(context.response.text)
    context.auth_token = a['token']

@given(u'I make {method} call with xml file {file_path}')
def step_impl(context, method, file_path):
    payload = {}
    a = open(file_path.replace('"', ''), 'rb')
    files = [
        ('file', (file_path, a, 'text/xml'))
    ]

    headers = {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + context.auth_token
    }
    print(headers)

    response = requests.request("POST", context.url, headers=headers, data=payload, files=files)
    context.response = response
    print(response.text)

@then(u'I read CSV')
def step_impl(context):
    location = context.response.headers['Location']
    file_name = str(location).split("/")[-1]
    # breakpoint()
    with open("/Users/MansiShah/episync/"+file_name) as csvFile:
        csvReader = csv.reader(csvFile, delimiter=',')
        for row in csvReader:
            print(row)

@then(u'compare generated csv with expected csv')
def step_impl(context):
    with open('csvFile', 'r') as csv1, open('final expected location', 'r') as csv2:
        actual = csv1.readlines()
        expected = csv2.readlines()

    # Create CSV file with differences
    with open('data_diff.csv', 'w') as outFile:
            for row in expected:
                if row not in actual:
                    outFile.write(row)
                else:
                    print("CSV is as expected")

    # Compare columns of two csvs
    # Expected columns in csv
    headers_list = ['episync_mmg_message_profile_identifier', 'episync_mmg_race', 'episync_mmg_local_subject_id','episync_mmg_ethnic_group', 'episync_mmg_sex','episync_mmg_subject_address_zip','episync_mmg_subject_address_state', 'episync_mmg_birth_date']
    import_headers = df.csvFile
        print(import_headers)







