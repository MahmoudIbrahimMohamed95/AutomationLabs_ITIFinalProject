package driverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import readers.PropertyReader;

public class FireFoxFactory extends AbstractDriver {

    private FirefoxOptions getOptions(){
        FirefoxOptions options=new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
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
        return new FirefoxDriver(getOptions());
    }

}
