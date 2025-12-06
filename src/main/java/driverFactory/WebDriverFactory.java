package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import readers.Log;
import readers.PropertyReader;

public class WebDriverFactory {


    private final static String browser= PropertyReader.getProperty("browserType");
    private static ThreadLocal<WebDriver> driverThreadLocal=new ThreadLocal<>();


    private static WebDriver getDriver(){
        //String browser=System.getProperty("browserType");
        Browser browserType=Browser.valueOf(browser.toUpperCase());
        Log.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver=browserType.getDriverFactory();
        return abstractDriver.createDriver();
    }

    public static WebDriver initDriver(){
        WebDriver driver = ThreadGuard.protect(getDriver());
        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }
    public static WebDriver get(){
        return driverThreadLocal.get();
    }
    public static void quitDriver(){
        driverThreadLocal.get().quit();
    }

}
