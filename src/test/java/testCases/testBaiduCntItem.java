package testCases;

import Utils.Listeners.Retry;
import Utils.TestInit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.BaiduSearch;
import pages.BaiduSetup;

public class testBaiduCntItem extends TestInit {
    BaiduSetup baiduSetup;
    BaiduSearch baiduSearch;


    @BeforeMethod
    public void openHomePage(){
        navigateToHomePage();
    }


    @Test(retryAnalyzer = Retry.class)
    public void cntItem() throws Exception{
        navigateToHomePage();
        //"搜索设置", 每页20条
        baiduSetup = new BaiduSetup();
        baiduSearch = new BaiduSearch();
        baiduSetup.clickableSetup(baiduSetup.btnSetup, baiduSetup.seachOption, baiduSetup.twenTy);
        baiduSetup.saveSetup();

        //搜索”沉思录“
        String str = "沉思录";
        baiduSearch.searchTXT(str);
        baiduSearch.waitSearchResult();
        baiduSearch.assertSearchResult(str);

        //百度所有返回结果计数
        int cnt = baiduSearch.searchResultList.size();
        logger.info("返回搜索结果，包含广告，百度百科 ，百度视频等内容共： " + cnt + "条记录");

        //百度正常搜索返回结果（包含百度夹带）计数
        int cntAd = baiduSearch.ads.size();
        logger.info("返回广告 " + cntAd + "条记录");

        //百度正常搜索返回结果（包含百度夹带）计数
        int count = baiduSearch.searchResult.size();
        baiduSearch.searchResult.forEach(x-> {
            if(x.getText().contains("其他人还在搜")){
                logger.info("出现“其他人还在搜”条目");
            }
        });
        count = count-1;
        logger.info("返回搜索结果 " + count + "条记录");
        assertEqualInt(count,20);
    }


    @AfterTest(description = "tear down")
    public void quit(){
        tearDown();
    }
}
