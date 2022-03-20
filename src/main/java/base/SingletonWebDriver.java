package base;

import enums.DriverType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class SingletonWebDriver {
    private static WebDriver driver;
    private static DriverType driverType=ReadConfigFile.getInstance().getDriverType();
    private static SingletonWebDriver instance;


    private SingletonWebDriver(){
        driver = createDriver();
    }

    public static SingletonWebDriver getInstance(){
        if(instance==null){
            instance = new SingletonWebDriver();
        }
        return instance;
    }

    //expose to user
    public WebDriver getDriver(){
        return driver;
    }

    private static WebDriver createDriver(){
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
                chromeOptions.addArguments("--verbose");
                chromeOptions.addArguments("--whitelisted-ips=''");
                capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
                driver = new ChromeDriver(chromeOptions);
            }
        }
        return driver;
    }
}
