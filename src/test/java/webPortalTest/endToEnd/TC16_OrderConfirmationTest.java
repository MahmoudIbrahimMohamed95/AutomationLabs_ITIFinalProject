package webPortalTest.endToEnd;

import assertions.CartPageAssertions;
import assertions.CheckoutPageAssertions;
import assertions.ConfirmationPageAssertions;
import businessPages.*;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import readers.JsonReader;
import readers.JsonSystemLoader;

@Epic("QA-Automation Labs")
@Feature("UI Confirmation Management")
@Story("Order Confirmation Page")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC16_OrderConfirmationTest {
    private WebDriver driver;
    //private JsonReader jsonReader;
    private JsonSystemLoader jsonLoader;
    private  IQA_ProductPage product;
    private CheckoutPageAssertions CheckoutAssertions;
    private CartPageAssertions CartAssertions;
    private ConfirmationPageAssertions Assertions;

    @BeforeClass
    public void setup() {
        //jsonReader = new JsonReader("Confirmation");
        JsonSystemLoader.loadFromFile("Confirmation");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        CheckoutAssertions=new CheckoutPageAssertions();
        CartAssertions=new CartPageAssertions();
        Assertions = new ConfirmationPageAssertions();
    }
    @Description("Login to the application")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                    .performLogin(JsonSystemLoader.getDynamicJsonData("ConfirmationLogin.username"),
                            JsonSystemLoader.getDynamicJsonData("ConfirmationLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("Navigating to certain category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
               .clickCategory(JsonSystemLoader.getDynamicJsonData("ConfirmationCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("Adding product to cart")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateAddingProductToCart(){
        QA_Cart cart=product.addToCart(JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.Name"))
                            .clickOnCartIcon();
        CartAssertions.validateCertainCartProductDetails(cart
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.Name")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedName")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedPrice")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedQty")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice"))

                      .assertEqual(JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice")
                                   ,cart.getCartTotalPrice(),"Error in Calculating Total Price")
                      .assertTrue(cart.isCartPageLoaded(),"Error in Cart Page");
    }
    @Description("Proceeding to checkout")
    @Test(dependsOnMethods = {"validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateProceedingToCheckout() {
        QA_CheckOut checkout = new QA_Cart(driver)
                              .proceedToCheckout();
        CheckoutAssertions.validateCertainCheckoutProductDetails(checkout
                                ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.Name")
                                ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedName")
                                ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedPrice")
                                ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedQty")
                                ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice"))
                        .assertEqual(JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice")
                                     ,checkout.getTotalPrice(),"Error in Calculating Total Price");

    }
    @Description("Confirming order")
    @Test(dependsOnMethods = {"validateProceedingToCheckout","validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateConfirmationPageDetails(){
        QA_Confirmation confirm=new QA_CheckOut(driver)
                .fillForm(JsonSystemLoader.getDynamicJsonData("ConfirmationForm.FirstName"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.MiddleName"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.LastName"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.Email"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.PhoneNumber"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.Address"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.State"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.City"),
                        JsonSystemLoader.getDynamicJsonData("ConfirmationForm.PinCode"))
                .clickOnContinueBtn();

        Assertions.validateConfirmationDetails(confirm
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.FirstName")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.MiddleName")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.LastName")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.Email")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.PhoneNumber")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.Address")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.State")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.City")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationForm.PinCode"))

                .validateConfirmationOfCertainProductDetails(confirm
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.Name")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedName")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedPrice")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedQty")
                        ,JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice"))
                .assertEqual(confirm.getPageTitle(),JsonSystemLoader.getDynamicJsonData("ConfirmationExpectedTittle"),"Error in Proceeding to Checkout")
                .assertEqual(JsonSystemLoader.getDynamicJsonData("ConfirmationProduct.ExpectedTotalPrice")
                        ,confirm.getTotalOrder(),"Error in Calculating Total Price");


    }
    @Description("Submit order")
    @Test(dependsOnMethods = {"validateConfirmationPageDetails","validateProceedingToCheckout","validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateSubmitOrder() {
        QA_Thanks thanks = new QA_Confirmation(driver)
                  .clickOnPlaceOrder();
        Assertions.assertEqual(thanks.getPageTitle(),JsonSystemLoader.getDynamicJsonData("ThanksExpectedTittle"), "Error in Page Title");
    }
    @Description("Shop again")
    @Test(dependsOnMethods = {"validateSubmitOrder","validateConfirmationPageDetails","validateProceedingToCheckout","validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateShopAgain() {
        QA_Shop shop = new QA_Thanks(driver)
                .clickShopAgain();
        Assertions.assertTrue(shop.isShopPageLoaded(), "Error in Page Title");
    }
    @AfterClass
    public void teardown() {
        driver.quit();
    }

}