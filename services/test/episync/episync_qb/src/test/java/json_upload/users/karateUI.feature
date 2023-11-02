Feature: Login to NBS
  Scenario: Login to localhost NBS website and Verify number of questions generated for the
    * configure driver = { type: 'chrome' }
#    * configure driverTarget = { docker: 'ptrthomas/karate-chrome', showDriverLog: true }
    Given driver 'http://localhost:7001/nbs/login'
    Then match driver.title == "NBS"
    And input('#id_UserName','jjw')
    * driver.screenshot()
    And input('#id_Password','jjw')
    * driver.screenshot()
    Then click('img#id_Submit_top_ToolbarButtonGraphic')
    And click('{a}System Management')
    * driver.screenshot()
    Then match driver.title == "NBS:System Management"
    * driver.screenshot()
    Then locate('/html/body/div/div/div[2]/div/table[6]/thead/tr/th/a').highlight()
    Then click('/html/body/div/div/div[2]/div/table[6]/thead/tr/th/a')
    Then click('/html/body/div/div/div[2]/div/table[6]/tbody/tr/td/table/tbody/tr[4]/td/a')
#    Then match driver.title == "NBS: Manage Templates"
    Then click('/html/body/form/div[1]/div[1]/div[4]/div/fieldset/table/tbody/tr/td/table/tbody/tr[20]/td[1]/a')
    * driver.screenshot()
    * delay(1000).screenshot()
    Then match driver.title == "NBS: Manage Templates"
    * driver.screenshot()
    * def elements = locateAll('span.InputFieldLabel')
    * print karate.sizeOf(elements)
    * match karate.sizeOf(elements) == 56
    Then print 'list--',elements





