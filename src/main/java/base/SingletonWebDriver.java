package base;

import enums.DriverType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class SingletonWebDriver {
    private static WebDriver driver;
    private static DriverType driverType=ReadConfigFile.getInstance().getDriverType();
    private static SingletonWebDriver instance;


    private SingletonWebDriver(){driver=createDriver();}

    public static SingletonWebDriver getInstance(){
        if(instance==null){
            instance = new SingletonWebDriver();
        }
        return instance;
    }

    //expose to user
    public WebDriver getDriver(){
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver createDriver(){
        switch(driverType){
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();

                driver = new FirefoxDriver();
            }
            case IE -> {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--verbose");
                chromeOptions.addArguments("--whitelisted-ips=''");
                //禁止显示“Chrome正在受到自动软件的控制”字样
                //chromeOptions.addArguments("--disable-infobars");  //out of date
                //chromeOptions.setExperimentalOption("userAutomationExtension",false);
                chromeOptions.setExperimentalOption("excludeSwitches",new String[]{"enable-automation"});
                capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
                driver = new ChromeDriver(capabilities);

            }
        }
        return driver;
    }
}
