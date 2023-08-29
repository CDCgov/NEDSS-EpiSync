Feature: Connect to DB

    Background: 
    * def DbUtils = Java.type('json_upload.users.DbUtils')
    * def db = new DbUtils(config)
    * def config = { username: 'sa', password: 'fake.fake.fake.1234', url: 'jdbc:sqlserver://localhost:1433; databaseName=restore.db', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
    

    Scenario: Read Rows in WA_Template Table
        * def template = db.readValue("'select wa_template_uid from WA_template where wa_template_uid='1000319'")
        Then print 'template--',template