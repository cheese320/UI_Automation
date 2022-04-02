package pages;


import Utils.TestInit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class BaiduSetup extends TestInit {
    /**
     * Elements
     */
    //"设置"
    @FindBy(xpath="//*[@id=\"s-usersetting-top\"]")
    public WebElement btnSetup;

    /**
     * "搜索设置“ 选项可选设置
     */
    //"搜索设置" 选项
    @FindBy(xpath="//*[@id=\"s-user-setting-menu\"]/div/a[1]/span")
    public WebElement seachOption;

    //“搜索结果显示条数”， “每页20条”选项
    @FindBy(xpath="//*[@id=\"nr_2\"]")
    public WebElement twenTy;

    //"保存设置“ 按钮
    @FindBy(xpath="//*[@id=\"se-setting-7\"]/a[2]")
    public WebElement saveSetupBtn;


    /**
     * "高级搜索“ 选项可选设置
     */
    //"高级搜索” 选项
    @FindBy(xpath="//*[@id=\"s-user-setting-menu\"]/div/a[2]")
    public WebElement advancedSetup;

    //"时间下拉框”
    @FindBy(xpath="//*[@id=\"adv-setting-gpc\"]/div")
    public WebElement durationOption;

    //"最近一天“选项
    @FindBy(xpath="//*[@id=\"adv-setting-gpc\"]//p[2]")
    public WebElement optionName;

    //"包含全部关键词”
    @FindBy(xpath="//*[@id=\"advanced\"]/div/form/ul/li[1]/div[1]/input")
    public WebElement allKey;

    //"包含完整关键词“
    @FindBy(xpath="//*[@id=\"advanced\"]/div/form/ul/li[1]/div[2]/input")
    public WebElement completeKey;

    //"包含任意关键词“
    @FindBy(xpath="//*[@id=\"advanced\"]/div/form/ul/li[1]/div[3]/input")
    public WebElement anyKey;

    //"不包含关键词“
    @FindBy(xpath="//*[@id=\"advanced\"]/div/form/ul/li[1]/div[4]/input")
    public WebElement exclusiveKey;


    //"高级搜索“ 按钮
    @FindBy(xpath="//*[@id=\"adv-setting-8\"]/input")
    public WebElement advancedSetupBtn;


    /**
     * Actions
     */
    //设置(mainSetup)=>设置选项(item)
    private void goToSetupItem(WebElement mainSetup, WebElement item){
        //进入设置
        mainSetup.click();
        //进入特定的设置选项
        item.click();
        logger.info("进入设置=>" + item.getText());
    }


    //设置(mainSetup)=>设置选项(item)=>具体设置(setup), 可点击元素
    public void clickableSetup(WebElement mainSetup, WebElement item, WebElement setup){
        goToSetupItem(mainSetup,item);
        //具体设置事项
        setup.click();
        alertAccept();
        logger.info("选择 "+setup.getAttribute("value"));
    }

    //设置(mainSetup)=>设置选项(item)=>具体设置下拉框(setup), 下拉框元素
    public void nonSelectableDropDown(WebElement mainSetup, WebElement item, WebElement durationOption, WebElement optionName){
        goToSetupItem(mainSetup,item);
        //具体设置事项
        durationOption.click();
         optionName.click();
        logger.info("选择 "+ "\"" + "最近一天" + "\"");
    }

    //保存设置
    public void saveSetup(){
        saveSetupBtn.click();
        alertAccept();
        logger.info("保存设置");
    }


    //alert accept
    public void alertAccept(){
        if(alertPresent()){
            logger.info("alert window pops up");
            driver.switchTo().alert().accept();
        }
    }

    //判断是否有alert
    private boolean alertPresent(){
        var alert = ExpectedConditions.alertIsPresent().apply(driver);
        return (alert!=null);
    }

    //navigate to trade page
    public void navigateToTradePage(){
        //btnTrade.click();
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
