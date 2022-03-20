package Utils;


import base.ReadConfigFile;
import base.SingletonWebDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class TestInit {
    public static WebDriver driver;
    public static Logger logger = null;
    public static Actions actions;

    public TestInit(){
        logger = LogManager.getLogger();
        driver = SingletonWebDriver.getInstance().getDriver();
        PageFactory.initElements(driver,this);
        actions = new Actions(driver);
    }

    //navigate to home page
    protected void navigateToHomePage(){
        logger.info("open Home page.");
        driver.get(ReadConfigFile.getInstance().getHomeURL());
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5),this);
    }


    /**
     * Actions
     */
    //press Shift
    protected void pressShift(WebElement element){
        actions.keyDown(element, Keys.SHIFT).perform();
    }

    //release shift
    protected void releaseShift(WebElement element){
        actions.keyUp(element, Keys.SHIFT).perform();
    }

    //hover
    protected void hover(WebElement element){
        actions.moveToElement(element).perform();
    }

    //drag and drop
    protected void dragAndDrop(WebElement from, WebElement to){
        actions.dragAndDrop(from,  to).perform();
    }

    //js script execution
    protected void jsExe(String script, WebElement element){
        ((JavascriptExecutor) driver).executeScript(script, element);
    }


    /**
     * Assert
     */
    //assert String
//    protected void assertStr(String actual, String expect){
//        try{
//            Assert.assertEquals(actual, expect);
//            logger.info("Actual result /[" + actual + "/] matches with expectedd result : /[" + expect + "/]" );
//        } catch (AssertionError e){
//            logger.error("Actual result /[" + actual + "/] does not match with expectedd result : /[" + expect + "/]" );
//        }
//    }


    //assert string
    protected void assertStr(String actual, String expect){
        Assert.assertEquals(actual, expect);
        logger.info("expect result : " + expect);
        logger.info("actual result : " + actual);
    }

    /**
     * Snapshot
     */
    //take snapshot
    protected void takeSnapShot() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());
        String screenshotFilePath = System.getProperty("user.dir")+"//testResult//snapshots//" + date + ".png";

        try{
            if(driver instanceof  TakesScreenshot){
                TakesScreenshot screenshot = ((TakesScreenshot) driver);
                File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);

                File destFile = new File(screenshotFilePath);
                FileUtils.copyFile(SrcFile,destFile);
                logger.info("Snapshot captured " + screenshotFilePath);
            }
        } catch (Exception e){
            throw new RuntimeException("Failed to capture snapshot");
        }
    }


    /**
     * Tear down
     */
    //driver quit
    public void tearDown() {
        driver.close();
        //may throw exception: "org.openqa.selenium.os.ProcessUtils killWinProcess
        //driver.quit();
    }
}
