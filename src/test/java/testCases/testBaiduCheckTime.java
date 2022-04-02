package testCases;

import Utils.Listeners.Retry;
import Utils.TestInit;
import base.SingletonWebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.BaiduSearch;
import pages.BaiduSetup;

public class testBaiduCheckTime  extends TestInit {
    BaiduSetup baiduSetup;
    BaiduSearch baiduSearch;

    @BeforeMethod
    public void openBrowser(){
        navigateToHomePage();
    }

    @Test(retryAnalyzer = Retry.class)
    public void checkTime() throws Exception{
        //"高级搜索", 当天内容
        baiduSetup = new BaiduSetup();
        baiduSearch = new BaiduSearch();
        baiduSetup.nonSelectableDropDown(baiduSetup.btnSetup,
                baiduSetup.advancedSetup,
                baiduSetup.durationOption,
                baiduSetup.optionName);

        //设置关键词”沉思录“, 保存设置
        String str = "沉思录";
        //String str = "录音笔";
        baiduSetup.completeKey.sendKeys(str);
        baiduSetup.advancedSetupBtn.click();

        //搜索结果在新窗口打开，跳转新窗口
        switchToNewWindow();
        Thread.sleep(2000);
        baiduSearch.timeOfSearchResult.forEach(x -> {
            //有时候返回的条目最前面不是时间信息
            if(x.getText().matches(".*\\d.*")){
                assertLessThanInt(Integer.parseInt(x.getText().replaceAll("[\\D]","")),24);
            }
        });
    }


    @AfterTest(description = "tear down")
    public void quit(){
        tearDown();
    }
}
