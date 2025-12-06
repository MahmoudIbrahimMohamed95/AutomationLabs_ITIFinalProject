package driverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import readers.Log;
import readers.PropertyReader;

import java.net.URI;

public class ChromeFactory extends AbstractDriver {
    private ChromeOptions getOptions(){
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        switch (PropertyReader.getProperty("executionType"))
        {
            case "LocalHeadless", "Remote" ->{
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
            }
        }
        return options;
    }
    @Override
    public WebDriver createDriver() {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") )
        {
            return new ChromeDriver(getOptions());
        }
        else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://"+ remoteHost+ ":" +remotePort + "/wd/hub").toURL(), getOptions()
                );
            }
            catch (Exception e) {
                Log.error("Error creating RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Failed to create RemoteWebDriver", e);
            }

        }
        else {
            Log.error("Invalid execution type: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("executionType"));
        }
    }


}
