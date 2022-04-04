package pages;

import Utils.TestInit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

public class BaiduSearch extends TestInit {
    /**
     * Elements
     */
    //"搜索框"
    @FindBy(xpath="//*[@id=\"kw\"]")
    WebElement searchBox;

    //"百度一下" 按钮
    @FindBy(xpath="//*[@id=\"su\"]")
    WebElement searchBtn;

    //百度搜索结果返回框
    @FindBy(xpath="//*[@id=\"content_left\"]")
    public WebElement searchResultContainer;

    //百度广告
    @FindBy(xpath="//*[@class=\"_2z1q32z\"]")
    public List<WebElement> ads;

    //百度搜索结果合集, 包含广告，百度百科，别人搜索结果等
    @FindBy(xpath="//*[contains(@class,\"c-container\")]")
    public List<WebElement> searchResultList;

    //百度搜索结果合集, 包含广告，百度百科，别人搜索结果等
    @FindBy(className="t")
    public List<WebElement> searchResultListByClassName;

    //百度搜索结果合集, 包含广告，百度百科，别人搜索结果等
    @FindBy(tagName="h3")
    public List<WebElement> searchResultListByTagName;

    //百度搜索结果合集： , 包含广告，百度百科，别人搜索结果等
    @FindBy(xpath=".//*[@id=\"content_left\"]//*[contains(@class,\"new-pmd\")]")
    public List<WebElement> searchResultByPartialClassName;

    //百度搜索结果合集：正常搜索结果，包含百度夹带（第二个和第三个）
    @FindAll({
            @FindBy(xpath = "//*[contains(@class,\"result c-container\")]"),
            @FindBy(xpath = "//*[contains(@class,\"result-op c-container\")]"),
    })
    public List<WebElement> searchResult;

    //百度搜索结果，时间状态
    //@FindBy(xpath="//*[@class=\"c-color-gray2\"]")
    @FindBy(xpath="//*[contains(@class,\"result c-container\")]//*[@class=\"c-color-gray2\"]")
    public List<WebElement> timeOfSearchResult;


    /**
     * Actions
     */
    //搜索
    public void searchTXT(String txt){
        searchBox.sendKeys(txt);
        searchBtn.click();
        logger.info("开始搜索 \"" + txt + "\"");
    }

    //assert search result
    public void assertSearchResult(String searchTxt){
        String actual = driver.getTitle();
        assertStrContains(actual, searchTxt);
    }

    //wait till search result present
    public void waitSearchResult(){
        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,\"result c-container\")]")));
    }

}
