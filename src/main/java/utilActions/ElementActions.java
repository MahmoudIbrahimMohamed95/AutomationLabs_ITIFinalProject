package utilActions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import readers.Log;

import java.util.List;

public class ElementActions {

    private WebDriver driver;
    private WaitHandler wait;
    public ElementActions(WebDriver driver ) {
        this.driver = driver;
        this.wait =new WaitHandler(driver);
    }
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public ElementActions click(By locator) {
        wait.fluentWait().until(d ->
            {
                try {
                    scrollToElementJS(locator);
                    d.findElement(locator).click();
                    Log.info("Element clicked: " + locator);
                    return true;
                }
                catch (Exception e) {
                    return false;
                }
            }
        );
        return this;
    }
    public ElementActions type(By locator, String text) {
        wait.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        new Actions(d).scrollToElement(element).perform();
                        element.clear();
                        element.sendKeys(text);
                        Log.info("Text entered in element: " + locator + " - Text: " + text);
                        return true;
                    }
                    catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }
    public String getText(By locator) {

        return wait.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        new Actions(d).scrollToElement(element).perform();
                        String msg= element.getText();
                        Log.info("Text retrieved from element: " + locator + " - Text: " + msg);
                        return !msg.isEmpty()?msg:null;
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
        );
    }
    public String getCertainAttribute(By locator,String attribute) {

        return wait.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        new Actions(d).scrollToElement(element).perform();
                        String value= element.getDomAttribute(attribute);
                        Log.info("Attribute value retrieved from element: " + locator + " - Text: " + value);
                        return !value.isEmpty()?value:null;
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
        );
    }
    public ElementActions selectFromDropdown(By locator, String value) {
        wait.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        new Actions(d).scrollToElement(element).perform();
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        Log.info("Selected value '" + value + "' from dropdown: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }
    public void scrollToElementJS(By locator) {
        wait.fluentWait().until(d ->
        {
            try {

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(""" 
                                arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", driver.findElement(locator));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
    public ElementActions hover(By locator) {
        wait.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        Log.info("Hovered over element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }
    public List<WebElement> getWebElements(By locator) {
        return wait.fluentWait().until(driver -> {
            try {
                return driver.findElements(locator);
            } catch (Exception e) {
                return null;
            }
        });
    }
}