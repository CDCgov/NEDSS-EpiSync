Feature: sample karate test script
  
  Background:
    * url 'http://172.17.0.2:8088/feed/upload'

  Scenario: get valid response for Generic V2 json
    Given path 'mmgat'   
    Given multipart file file = { read: 'data/Generic-MMG-V2.0.json', filename: 'Generic-MMG-V2.0.json', contentType: 'application/json'}
    When method post
    Then status 200
    Then match response contains "File upload success"
  