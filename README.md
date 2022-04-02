



# UI_Automation

This is a UI automation framework, implemented using java, selenium web-driver, log4j and TestNG. Page factory pattern is used to initialize web elements which are defined in page objects.



## gitclone

git@github.com:cheese320/UI_Automation.git





## Prerequisite

1. Java JDK 11 or higher

2. Apache maven 3 or higher

   

## Coverage (To be continued)

| Category        | Description                 | Status |
| --------------- | --------------------------- | ------ |
| Framework level | Page Factory design pattern | Done   |
|                 | Data-driven                 | Done   |
|                 | TestNG                      | Done   |
|                 | Log4j                       | Done   |
|                 | Extent Report               | Done   |
| Functionality   | Retry                       | Done   |
|                 | Press Shift                 | Done   |
|                 | Release Shift               | Done   |
|                 | Hover                       | Done   |
|                 | Drag and Drop               | Done   |
|                 | js script execution         | Done   |
|                 | take snapshot               | Done   |
|                 | switch to window            | Done   |
|                 | drop down list              | Done   |
|                 | alert                       | Done   |

## Test Architecture

1. Config : ~/src/main/java/config.propertities
2. Page object :  ~/src/test/java/pages
3. testDate : ~/src/test/java/testData
4. test cases : ~/src/test/java/testCases
5. test execution : right click testng.xml and select "Run" 
6. test report: ~/extentReports
