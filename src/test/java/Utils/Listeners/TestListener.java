package Utils.Listeners;

import Utils.ExtentReports.ExtentManager;
import Utils.TestInit;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;


public class TestListener extends TestInit implements ITestListener{
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //cases in paralle run

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " +
                result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Successful</b>";
        Markup mk = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS,mk);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                "Exceptoin Occured, click to see details: " + "</font></b></summary>" +
                exceptionMessage.replaceAll(",","<br>") + "</details> \n");
        /*
        WebDriver webDriver = ((TestInit)result.getInstance()).driver;
        String path = takeScreenshot(webDriver,result.getMethod().getMethodName());
        */
        String path = takeScreenshot(result.getMethod().getMethodName());
        try{
            extentTest.get().fail("<b><font color=red" + "Screenshot of failure" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch(Exception e){
            extentTest.get().fail("Test Failed, cannot attach screenshot");
        }

        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Failed</b>";
        Markup mk = MarkupHelper.createLabel(logText, ExtentColor.RED);
        extentTest.get().log(Status.FAIL,mk);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
        Markup mk = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP,mk);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        if(extent != null){
            extent.flush();
        }
    }

    /*public String takeScreenshot(WebDriver driver, String methodName){
        String fileName = getScreenshotName(methodName);
        String directory = System.getProperty("user.dir") + "/screenshots/";
        new File(directory).mkdirs();
        String path = directory + fileName;

        try{
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot,new File(path));
        } catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getScreenshotName(String methodName){
        Date d = new Date();
        String fileName = methodName + d.toString().replace(":","_").replace(" ","_") + ".png";
        return fileName;
    }*/
}
