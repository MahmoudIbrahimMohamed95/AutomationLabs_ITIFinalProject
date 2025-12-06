package driverFactory;

import org.openqa.selenium.WebDriver;
import readers.PropertyReader;

public abstract class AbstractDriver {
    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");

    public abstract WebDriver createDriver();

}
