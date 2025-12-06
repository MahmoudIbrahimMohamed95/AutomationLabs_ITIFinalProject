package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import readers.Log;
import utilActions.ElementActions;

public class QA_ProductDetails {

    private WebDriver driver;
    private static ElementActions actions;
    private static short postCartCounter=0;


    private static final By addToCartButtonLocator=By.cssSelector("button.addToCart");
    private static final By cartIcon =By.cssSelector("a>i.fa-2x");
    private static final By cartCounter=By.id("cartCount");
    private static final By logOutBtn=By.id("logoutBtn");
    private static final By breadcrumbLocator=By.cssSelector("span.breadcrumb-item");
    private static final By backBtn =By.cssSelector(".fa-backward");


    private static By ProductNameLocator(String productName){
        return By.xpath(String.format("//h3[.='%s']", productName));
    }
    private static By ProductPriceLocator(String productName){
     return By.xpath( "//h3[text()='" + productName + "']/following-sibling::h3[@class='font-weight-semi-bold mb-4']");

    }
    public QA_ProductDetails(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

    public QA_ProductDetails clickOnAddToCartButton(){
        actions.click(addToCartButtonLocator);
        postCartCounter++;
        return this;
    }
    public QA_Cart clickOnCartIcon(){
        actions.click(cartIcon);
        return new QA_Cart(driver);
    }
    public QA_ProductDetails clickOnLogOutBtn(){
        actions.click(logOutBtn);
        return this;
    }
    public String getProductName(String productName){
        return actions.getText(ProductNameLocator(productName));
    }

    public String getProductPrice(String productName){
        return actions.getText(ProductPriceLocator(productName));
    }

    public String getCertainProductNavigationTittle(){
        return actions.getText(breadcrumbLocator);
    }

    private static String getCartCounter(){
        return actions.getText(cartCounter);

    }
    public boolean isCartCounterIncremented(){
        return  (Short.parseShort(getCartCounter())==postCartCounter)? true : false;
    }
}
