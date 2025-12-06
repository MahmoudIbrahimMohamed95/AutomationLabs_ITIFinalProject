package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_Thanks {
    WebDriver driver;
    ElementActions actions;
    private static final By breadcrumbLocator=By.cssSelector("span.breadcrumb-item");
    private static final By shopAgain=By.linkText("Shop Again");
    public QA_Thanks(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }
    public String getPageTitle() {
        return  actions.getText(breadcrumbLocator);
    }
    public QA_Shop clickShopAgain() {
        actions.click(shopAgain);
        return new QA_Shop(driver);
    }
}
