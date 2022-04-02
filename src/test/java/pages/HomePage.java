package pages;


import Utils.TestInit;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends TestInit {
    /**
     * Elements
     */
    //USDT
    @FindBy(xpath="//*[@class=\"e-tabs\"]//*[text()=\"USDT\"]")
    WebElement tabUSDT;


    @FindBy(xpath="//*[@class=\"search-box\"]//*[@class=\"search-input\"]")
    WebElement searchBox;


    @FindBy(xpath="//*[@class=\"trade-list\"]//*[@type=\"button\"]")
    WebElement btnTrade;

    /**
     * Actions
     */
    //scroll to SearchBox
    public void findSearchBox(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",searchBox);
    }

    //search ZIL
    public void searchZIL(String searchText){
        searchBox.sendKeys(searchText);
        searchBox.sendKeys(Keys.ENTER);
        logger.info("search \"" + searchText + "\"");
        takeScreenshot();
    }

    //navigate to trade page
    public void navigateToTradePage(){
        btnTrade.click();
        try{
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        }
        logger.info("navigate to TradePage");
        takeScreenshot();
    }

    //assert search result
    public void assertSearchResult(String expect){
        String actual = driver.getCurrentUrl();
        assertStrEqual(actual, expect);
    }


}
