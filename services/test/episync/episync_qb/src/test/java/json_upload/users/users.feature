Feature: sample karate test script

  Background:
    * url 'http://172.19.0.4:8088/mmg/publish?type=MMG&url=http%3A%2F%2Fnedds.gov.us'
    * def config = { username: 'sa', password: 'fake.fake.fake.1234', url: 'jdbc:sqlserver://nbs-mssql:1433;Database=NBS_ODSE;Encrypt=True;TrustServerCertificate=True', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
    * def DbUtils = Java.type('json_upload.users.DbUtils')
    * def db = new DbUtils(config)


  Scenario: get valid response for Generic V2 json
    And header Accept = 'application/json'
    Given multipart file file = { read: 'data/Generic-MMG-V2.0.json', filename: 'Generic-MMG-V2.0.json', contentType: 'application/json'}
    When method post
    Then status 200
    * print response.message
    And match response.code == 'SUCCESS'
    * def respMessage = response.message.split('template_id:')[1].split(' ')[0]
    * print respMessage
    * def template = db.readRows("select * from dbo.WA_template where wa_template_uid = " + respMessage)
    Then print 'template--',template


  Scenario: Read Json and parse the json
    * def reqJson = read("data/Generic-MMG-V2.0.json")
    * print 'the value of dateofbirth is:', reqJson["testScenarios"][0]['testBlocks']['64c5e4c8-2dee-4ec1-b3f2-141db90e15bf']['instances'][0]['827d2f8f-eec9-49f0-9265-0fc681f120b5']['values'][0]['value']
