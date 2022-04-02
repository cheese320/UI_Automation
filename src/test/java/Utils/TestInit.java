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

  /*  public WebDriver getDriver(){
        return driver;
    }*/

    //navigate to home page
    protected void navigateToHomePage(){
        logger.info("open Home page.");
        driver.get(ReadConfigFile.getInstance().getHomeURL());
        //driver.manage().window().maximize();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20),this);
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

    //switch to new window
    protected void switchToNewWindow(){
        String currWinHandle = driver.getWindowHandle();
        driver.close();
        driver.getWindowHandles().forEach(winHandle -> {
            if(!winHandle.equals(currWinHandle)){
                driver.switchTo().window(winHandle);
            }
        });
    }

    /**
     * Assert
     */
    //assert string equals
    protected void assertStrEqual(String actual, String expect){
        logger.info("expect result : " + expect);
        logger.info("actual result : " + actual);
        Assert.assertEquals(actual, expect);
    }

    //assert string contains
    protected void assertStrContains(String actual, String expect){
        logger.info("expect result : " + expect);
        logger.info("actual result : " + actual);
        Assert.assertTrue(actual.contains(expect));
    }

    //assertEqual int
    protected void assertEqualInt(int actual, int expect){
        logger.info("expect result : " + expect + "; actual result : " + actual);
        Assert.assertEquals(actual, expect);
    }

    //assertMoreThan int
    protected void assertMoreThanInt(int actual, int benchmark){
        logger.info("benchmark : " + benchmark + "; actual result : " + actual);
        Assert.assertTrue(actual>benchmark);
    }

    //assertLessThan int
    protected void assertLessThanInt(int actual, int benchmark){
        logger.info("benchmark : " + benchmark + "; actual result : " + actual);
        Assert.assertTrue(actual<benchmark);
    }

    /**
     * Snapshot
     */
    //take screenshot
    protected void takeScreenshot() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());
        String screenshotFilePath = System.getProperty("user.dir")+"//screenshots//" + date + ".png";

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

    protected String takeScreenshot(String methodName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());
        String screenshotFilePath = System.getProperty("user.dir")+"//testResult//screenshots//" + methodName + date + ".png";

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
        return screenshotFilePath;
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
