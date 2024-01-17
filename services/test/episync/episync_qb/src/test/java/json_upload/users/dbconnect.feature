@ignore
Feature: Connect to DB

    Background:
        * def config = { username: 'sa', password: 'fake.fake.fake.1234', url: 'jdbc:sqlserver://nbs-mssql:1433;Database=NBS_ODSE;Encrypt=True;TrustServerCertificate=True', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver' }
        * def DbUtils = Java.type('json_upload.users.DbUtils')
        * def db = new DbUtils(config)

    Scenario: Read Rows in WA_Template Table
        * def question = db.readRows("select count(*) from dbo.WA_template")
        Then print 'template--',question

    Scenario: Read value of template id in WA_Template Table
        * def template = db.readRows("select * from dbo.WA_template where wa_template_uid = 1000315")
        Then print 'template--',template