package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_Cart {
    //Locators
    private static final By cartIcon =By.cssSelector("a>i.fa-2x");
    private static final By logOutBtn=By.id("logoutBtn");
    private static final By breadcrumbLocator=By.cssSelector("span.breadcrumb-item");
    private static final By backBtn =By.cssSelector(".fa-backward");
    private static final By totalPrice=By.id("totalPrice");
    private static final By proceedToCheckOut=By.id("checkoutBtn");
    private static final By cartCounter= By.id("cartCount");

    //Variables
    WebDriver driver;
    ElementActions actions;

    //Constructor
    public QA_Cart(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

   private static final By cartBodyLocator=By.cssSelector("tbody>tr");
   private static By byIndexNameLocator(int productNumber){
       return By.cssSelector(String.format("tbody > tr:nth-child(%d) > td:nth-child(1)", productNumber));
   }
   private static By byIndexPriceLocator(int productNumber){
       return By.cssSelector(String.format("tbody >tr:nth-child(%d) > td:nth-child(2)", productNumber));
   }
   private static By byIndexQtyLocator(int productNumber)
   {
       return By.cssSelector(String.format("tbody >tr:nth-child(%d) > td:nth-child(3)" , productNumber));
   }
   private static By byIndexTotalLocator(int productNumber){
       return By.cssSelector(String.format("tbody >tr:nth-child(%d) > td:nth-child(4)", productNumber));
   }
   private static By byIndexRemoveLocator (int productNumber){

       return By.cssSelector(String.format("tbody >tr:nth-child(%d) > td:nth-child(5)",productNumber));
   }

   private static By productNameLocator(String productName){
       return By.xpath(String.format("//td[contains(text(), '%s')]",productName));
   }
    private static By productPriceLocator(String productName){

       return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[1]", productName));
    }

    private static By productQuantityLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[2]/input", productName));
    }

    private static By productTotalPriceLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[3]", productName));
    }

    private static By productRemoveLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[4]/button", productName));
    }

    public String getCartNavigationTittle() {
        return actions.getText(breadcrumbLocator);
    }
    public boolean isCartPageLoaded() {
       return actions.getText(breadcrumbLocator).contains("Cart");
    }
    public QA_CheckOut clickOnProceedToCheckout() {
        actions.click(proceedToCheckOut);
        return new QA_CheckOut(driver);
    }
    public String getProductName(String productName) {

       return actions.getText(productNameLocator(productName));
    }
    public String getProductPrice(String productName) {

        return actions.getText(productPriceLocator(productName));
    }
    public String getProductQuantity(String productName) {
        return actions.getCertainAttribute(productQuantityLocator(productName),"value");

    }
    public String getProductTotalPrice(String productName) {
        return actions.getText(productTotalPriceLocator(productName));
    }
    public QA_Cart removeProduct(String productName) {
        actions.click(productRemoveLocator(productName));
        return this;
    }
    public QA_Cart clickOnBackBtn() {
        actions.click(backBtn);
        return this;
    }
    public QA_Cart incrementProductQty(String productName,String qty) {
        actions.type(productQuantityLocator(productName), qty);
        return this;
    }

    public  String getCartCounter() {
        return actions.getText(cartCounter);

    }

    public String getCartTotalPrice() {
       return actions.getText(totalPrice).split(":")[1].trim();
    }
    public QA_CheckOut proceedToCheckout() {
       actions.click(proceedToCheckOut);
       return new QA_CheckOut(driver);
    }
}
