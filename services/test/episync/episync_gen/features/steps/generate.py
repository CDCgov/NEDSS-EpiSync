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

    context.response_file = "/Users/MansiShah/episync/"+file_name

@then(u'compare generated csv with expected csv')
def step_impl(context):

    with open(context.response_file, 'r') as csv1, open('/Users/MansiShah/Work/NEDSS-EpiSync/services/test/episync_gen/expected_csv.csv', 'r') as csv2:
        actual = csv1.readlines()
        expected = csv2.readlines()

    # Create CSV file with differences
    with open('data_diff.csv', 'w') as outFile:
        for row in expected:
            if row not in actual:
                outFile.write(row)
                assert False, "CSV is not as expected"
                # print("CSV is not as expected")

            else:
                print("CSV is as expected")
