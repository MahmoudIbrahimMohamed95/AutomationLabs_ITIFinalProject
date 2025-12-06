package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_Confirmation {
    WebDriver driver;
    private ElementActions actions;
    private static final By FirstName=By.xpath("//div[strong[text()='First Name:']]/following-sibling::div[1]");
    private static final By MiddleName=By.xpath("//div[strong[text()='Middle Name:']]/following-sibling::div[1]");
    private static final By LastName=By.xpath("//div[strong[text()='Last Name:']]/following-sibling::div[1]");
    private static final By Email=By.xpath("//div[strong[text()='Email:']]/following-sibling::div[1]");
    private static final By Phone=By.xpath("//div[strong[text()='Phone:']]/following-sibling::div[1]");
    private static final By Address=By.xpath("//div[strong[text()='Address:']]/following-sibling::div[1]");
    private static final By State=By.xpath("//div[strong[text()='State:']]/following-sibling::div[1]");
    private static final By City=By.xpath("//div[strong[text()='City:']]/following-sibling::div[1]");
    private static final By pinCode=By.xpath("//div[strong[text()='Pin Code:']]/following-sibling::div[1]");
    private static final By breadcrumbLocator=By.cssSelector("span.breadcrumb-item");

    private static final By totalOrder=By.id("totalPrice");
    private static final By placeOrder=By.linkText("Place Order");
    //constructor
    public QA_Confirmation(WebDriver driver )
    {
        this.driver=driver;
        actions=new ElementActions(driver);
    }
    private static By productNameLocator(String productName){
        return By.xpath(String.format("//td[contains(text(), '%s')]",productName));
    }
    private static By productPriceLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[1]", productName));
    }

    private static By productQuantityLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[2]", productName));
    }

    private static By productTotalPriceLocator(String productName){

        return By.xpath(String.format("//td[contains(text(),'%s')]/following-sibling::td[3]", productName));
    }

    public String getPageTitle() {
        return  actions.getText(breadcrumbLocator);
    }
    public String getFirstName() {
        return  actions.getText(FirstName);
    }
    public String getMiddleName() {
        return  actions.getText(MiddleName);
    }
    public String getLastName() {
        return  actions.getText(LastName);
    }
    public String getEmail() {
        return  actions.getText(Email);
    }
    public String getPhone() {
        return  actions.getText(Phone);
    }
    public String getAddress() {
        return  actions.getText(Address);
    }
    public String getState() {
        return  actions.getText(State);
    }
    public String getCity() {
        return  actions.getText(City);
    }
    public String getPinCode() {
        return  actions.getText(pinCode);
    }

    public String getProductName(String productName) {

        return actions.getText(productNameLocator(productName));
    }
    public String getProductPrice(String productName) {

        return actions.getText(productPriceLocator(productName));
    }
    public String getProductQuantity(String productName) {
        return actions.getText(productQuantityLocator(productName));

    }
    public String getProductTotalPrice(String productName) {
        return actions.getText(productTotalPriceLocator(productName));
    }
    public String getTotalOrder() {

        return actions.getText(totalOrder);
    }

    public QA_Thanks clickOnPlaceOrder() {
        actions.click(placeOrder);
        return new QA_Thanks(driver);
    }

}
