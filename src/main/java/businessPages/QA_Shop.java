package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_Shop {
    //attributes
    private static WebDriver driver;
    private static ElementActions actions;

    //locators
    By catagory=By.xpath("//div[@class='offer-text']/a[@href='mens-wear.php']");
    By shopBreadcrumb=By.cssSelector(".breadcrumb");

    //Constructor
    public QA_Shop(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
        }

    private static By categoryLocator(String category){
        By catagoryII =
                By.xpath(String.format("//div[@class='offer-text']/a[@href='%s.php']", category));
        return catagoryII;
    }
    private static By categoryDescriptionLocator(String category){
        By catagoryII =
                By.xpath(String.format("//h3[.='%s']", category));
        return catagoryII;
    }
    //Methods
    public boolean isShopPageLoaded(){
        return  actions.getText(shopBreadcrumb).contains("Shop");
    }
    public String getCategory(String category){
        return actions.getText(categoryDescriptionLocator(category));
    }

    public IQA_ProductPage clickCategory(String category){
        actions.click(categoryLocator(category));

        QA_Categories certainCategory = QA_Categories.fromKey(category);
        return certainCategory.createPage(driver);
    }


}
