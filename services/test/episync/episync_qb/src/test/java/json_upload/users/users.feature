@ignore
Feature: sample karate test script

  Background:
    * url 'http://episync-publish:8088/mmg/publish?type=MMG&url=http%3A%2F%2Fnedds.gov.us'
    * def config = { username: 'sa', password: 'fake.fake.fake.1234', url: 'jdbc:sqlserver://nbs-mssql:1433;Database=NBS_ODSE;Encrypt=True;TrustServerCertificate=True', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
    * def DbUtils = Java.type('json_upload.users.DbUtils')
    * def db = new DbUtils(config)


  Scenario Outline: get valid response for Generic V2 json
    And header Accept = 'application/hal+json'
    Given multipart file file = { read: 'data/<MMG>.json', filename: 'Generic-MMG-V2.0.json', contentType: 'application/json'}
    When method post
    Then status 200
    * print response.message
    And match response.code == 'SUCCESS'
#    And match response.message == "template_id:" + template_name:Generic Version 2.0.1 + questions:67"
    * def respMessage = response.message.split('template_id:')[1].split(' ')[0]
    * print respMessage
    * def template = db.readRows("select * from dbo.WA_template where wa_template_uid = " + respMessage)
    Then print 'template--',template
#    * def questions = db.readRows("select * from dbo.WA_question inner join dbo.WA_UI_metadata ui on q.question_identifier = ui.question_identifier")

    Examples:
      | MMG |
      | Generic-MMG-V2.0 |

  Scenario Outline: Read Json and parse the json
    * def reqJson = read("data/<MMG>.json")
    * print 'the value of dateofbirth is:', reqJson["testScenarios"][0]['testBlocks']['64c5e4c8-2dee-4ec1-b3f2-141db90e15bf']['instances'][0]['827d2f8f-eec9-49f0-9265-0fc681f120b5']['values'][0]['value']

  Examples:
    | MMG |
    | Generic-MMG-V2.0 |