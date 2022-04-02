package Utils.Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class Retry implements IRetryAnalyzer {
    private int count = 0;
    private static int maxTry = 1; //Run failed test twice

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()){
            if (count < maxTry){
                count++;
                iTestResult.setStatus(ITestResult.FAILURE);
                return true; //tell testNG to re-run test case
            }
        }
        return false;
    }
}
