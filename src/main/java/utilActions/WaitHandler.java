package utilActions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitHandler {
    private WebDriver driver;

    public WaitHandler(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> fluentWait(){

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(100))
                //.ignoring(Exception.class);
                .ignoreAll(getExceptions());
    }

    private ArrayList<Class<? extends Exception>>getExceptions(){
        //ArrayList<Exception> exceptions=new ArrayList<>();
        ArrayList<Class<? extends Exception>>exceptions=new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        //exceptions.add(ElementNotVisibleException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementClickInterceptedException.class);
        exceptions.add(ElementNotInteractableException.class);
        return exceptions;
    }
}
