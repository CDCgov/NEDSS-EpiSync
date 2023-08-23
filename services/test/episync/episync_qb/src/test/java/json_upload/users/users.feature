Feature: sample karate test script
  
  Background:
    * url 'http://172.17.0.2:8088/feed/upload'

  Scenario: get valid response for Generic V2 json
    Given path 'mmgat'   
    Given multipart file file = { read: 'data/Generic-MMG-V2.0.json', filename: 'Generic-MMG-V2.0.json', contentType: 'application/json'}
    When method post
    Then status 200
    Then match response contains "File upload success"
 
  Scenario: Read Json and parse the json
    * def reqJson = read("data/Generic-MMG-V2.0.json")
    * print 'the value of dateofbirth is:', reqJson["testScenarios"][0]['testBlocks']['64c5e4c8-2dee-4ec1-b3f2-141db90e15bf']['instances'][0]['827d2f8f-eec9-49f0-9265-0fc681f120b5']['values'][0]['value']
    

 