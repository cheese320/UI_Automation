package Utils.ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports createInstance(){
        String fileName = getReportName();
        String directory = System.getProperty("user.dir") + "./extentReports/";
        new File(directory).mkdirs();
        String path = directory + fileName;

        var sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Automation Reports");
        sparkReporter.config().setReportName("Automation Test Result");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        return extent;
    }

    public static String getReportName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());
        String fileName = "AutomationReport_" + date + ".html";
        return fileName;
    }

}
