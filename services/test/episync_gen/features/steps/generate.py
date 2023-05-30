import requests

@given('I have url endpoint "{url}"')
def step_impl(context, url):
    # print('Hello....')
    context.url = url
    print(url)

@given('I make "{method}" call with body "{body}"')
def step_impl(context, method, body):
    # print('world....')
    response = requests.get(context.url)
    context.response = response

@given('the response code is "{code}"')
def step_impl(context, code):
    if(int(code) == int(context.response.status_code)):
        print("Response code is "+ str(code) +" as expected..")
    else:
        exception_message="Response code not as expected. Expected: " + str(code) + " Actual: "+ str(context.response.status_code)
        raise Exception(exception_message)
    # raise NotImplementedError(u'STEP: Given the reponse is 404 error')

