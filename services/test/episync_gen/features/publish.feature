Feature: Verify token generation on publish REST API
#behave features/publish.feature -f plain --no-capture
#behave features/publish.feature -f html -o report.html

#    Scenario: Make a GET call to with invalid username
#      Given I have url endpoint "http://localhost:8088/oauth/token"
#      And I make "GET" call with body "{'username'='epi','password'='sync!'}"
#      And the response code is "404"

    Scenario: Make a POST call to with valid username
      Given I have url endpoint "http://localhost:8088/oauth/token"
      And I make "POST" call with body "{"username": "episync", "password": "secret"}"
      And the response code is "200"
      And I capture the auth token

      Given I have url endpoint "http://localhost:8088/feed/upload/xml"
      And I make "POST" call with xml file "NBSXML.xml"
#      And I make "POST" call with xml file "/Users/PATH_TO_XML.xml"
      And the response code is "201"
      And message is "{"code":201,"message":"Transform to CSV: success"}"