package testCases;

import Utils.ReadExcel;
import Utils.TestInit;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

public class testDemo extends TestInit {
    HomePage homePage;

    @BeforeMethod(description = "open Home page")
    public void openHomePage(){
        navigateToHomePage();
    }

    @Test
    public void searchTXT(){
        homePage = new HomePage();
        //homePage: go to tab USDT, find search box;
        homePage.findSearchBox();
        //get test data
        ReadExcel readExcel = new ReadExcel("HomePage");
        String searchText = readExcel.getCellValueByColName(1,"Input");
        logger.info("Get test data.");
        //search
        homePage.searchZIL(searchText);
        //click trade
        homePage.navigateToTradePage();
        //get actual result
        String actualResult = readExcel.getCellValueByColName(1,"Expect Result");
        //assert
        homePage.assertSearchResult(actualResult);

    }


    @AfterTest(description = "tear down")
    public void quit(){
        tearDown();
    }
}
