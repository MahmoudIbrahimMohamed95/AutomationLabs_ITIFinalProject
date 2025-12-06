package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;


public class QA_CheckOut {
    private static final By firstName = By.id("firstname");
    private static final By middleName = By.id("middlename");
    private static final By lastName = By.id("lastname");
    private static final By emailAddress = By.id("email");
    private static final By phoneNumber = By.id("phone");
    private static final By address = By.id("address");
    private static final By state = By.id("states");
    private static final By city = By.id("city");
    private static final By pinCode = By.id("pincode");
    private static final By continueBtn = By.id("continue");
    private static final By totalPrice = By.id("totalPrice");
    private static final By breadcrumbLocator=By.cssSelector("span.breadcrumb-item");
    private static final By addressError = By.cssSelector("#addresserror.error");
    private static final By emailError = By.cssSelector("#emailerror.error");
    private static final By PhoneError = By.cssSelector("#phoneerror.error");

    private  WebDriver driver;
    private ElementActions actions;
    public QA_CheckOut(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

    private static By byIndexNameLocator(int productNumber){
        return By.cssSelector(String.format("table > tr:nth-child(%d) > td:nth-child(1)", productNumber));
    }
    private static By byIndexPriceLocator(int productNumber){
        return By.cssSelector(String.format("table >tr:nth-child(%d) > td:nth-child(2)", productNumber));
    }
    private static By byIndexQtyLocator(int productNumber)
    {
        return By.cssSelector(String.format("table >tr:nth-child(%d) > td:nth-child(3)" , productNumber));
    }
    private static By byIndexTotalLocator(int productNumber){
        return By.cssSelector(String.format("table >tr:nth-child(%d) > td:nth-child(4)", productNumber));
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



    public QA_CheckOut fillForm(String fName, String mName, String lName, String mailAddress,
                         String mobileNumber,String addressI ,
                         String stateI, String cityI, String pinCodeI)
    {
        actions.type(firstName, fName).
                type(middleName, mName).
                type(lastName, lName).
                type(emailAddress, mailAddress).
                type(phoneNumber, mobileNumber).
                type(address, addressI).
                type(state, stateI).
                type(city, cityI).
                type(pinCode, pinCodeI);

        return this;
    }
    public QA_Confirmation clickOnContinueBtn(){
        actions.click(continueBtn);
        return new QA_Confirmation(driver);
    }
    public QA_CheckOut clickContinueBtn(){
        actions.click(continueBtn);
        return this;
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
    public String getTotalPrice() {

        return actions.getText(totalPrice);
    }
    public String getCheckOutNavigationTittle(){
        return actions.getText(breadcrumbLocator);
    }
    public String getAddressError(){
        return actions.getText(addressError);
    }
    public String getEmailError(){
        return actions.getText(emailError);
    }
    public String getPhoneError(){
        return actions.getText(PhoneError);
    }


}
