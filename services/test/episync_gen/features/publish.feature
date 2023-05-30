Feature: Verify token generation on publish REST API
#behave features/publish.feature -f plain --no-capture
#behave features/publish.feature -f html -o report.html

    Scenario: Make a GET call to with invalid username
      Given I have url endpoint "http://localhost:8088/oauth/token"
      And I make "GET" call with body "{'username'='epi','password'='sync!'}"
      And the response code is "404"

    Scenario: Make a GET call to with invalid username
      Given I have url endpoint "http://localhost:8088/oauth/token"
      And I make "GET" call with body "{'username'='epi','password'='sync!'}"
      And the response code is "200"
