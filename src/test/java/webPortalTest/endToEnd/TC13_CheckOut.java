package webPortalTest.endToEnd;

import assertions.CartPageAssertions;
import assertions.CheckoutPageAssertions;
import businessPages.*;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import readers.JsonReader;

@Epic("QA-Automation Labs")
@Feature("UI Checkout Management")
@Story("Checkout process")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC13_CheckOut {
    private WebDriver driver;
    private JsonReader jsonReader;
    private  IQA_ProductPage product;
    private CheckoutPageAssertions Assertions;
    private CartPageAssertions CartAssertions;
    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("CheckOut");
        //JsonSystemLoader.loadFromFile("QALogin-testData");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new CheckoutPageAssertions();
        CartAssertions=new CartPageAssertions();
    }
    @Description("validate login to the application")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                .performLogin(jsonReader.getJsonData("CheckOutLogin.username"),
                              jsonReader.getJsonData("CheckOutLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("validate navigating to certain category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
                .clickCategory(jsonReader.getJsonData("CheckOutCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("validate adding product to cart")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateAddingProductToCart(){
        QA_Cart cart=product.addToCart(jsonReader.getJsonData("CheckOutProduct.Name"))
                            .clickOnCartIcon();
        CartAssertions.validateCertainCartProductDetails(cart
                         ,jsonReader.getJsonData("CheckOutProduct.Name")
                         ,jsonReader.getJsonData("CheckOutProduct.ExpectedName")
                         ,jsonReader.getJsonData("CheckOutProduct.ExpectedPrice")
                         ,jsonReader.getJsonData("CheckOutProduct.ExpectedQty")
                         ,jsonReader.getJsonData("CheckOutProduct.ExpectedTotalPrice"))
                      .assertEqual(jsonReader.getJsonData("CheckOutProduct.ExpectedTotalPrice")
                                  ,cart.getCartTotalPrice(),"Error in Calculating Total Price")
                      .assertTrue(cart.isCartPageLoaded(),"Error in Cart Page");
    }
    @Description("validate proceeding to checkout")
    @Test(dependsOnMethods = {"validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateProceedingToCheckout() {
        QA_CheckOut checkout = new QA_Cart(driver)
                    .proceedToCheckout();
        Assertions.validateCertainCheckoutProductDetails(checkout
                        ,jsonReader.getJsonData("CheckOutProduct.Name")
                        ,jsonReader.getJsonData("CheckOutProduct.ExpectedName")
                        ,jsonReader.getJsonData("CheckOutProduct.ExpectedPrice")
                        ,jsonReader.getJsonData("CheckOutProduct.ExpectedQty")
                        ,jsonReader.getJsonData("CheckOutProduct.ExpectedTotalPrice"))

                  .assertEqual(jsonReader.getJsonData("CheckOutProduct.ExpectedTotalPrice")
                             ,checkout.getTotalPrice(),"Error in Calculating Total Price");

    }
    @Description("validate proceeding to confirmation page")
    @Test(dependsOnMethods = {"validateProceedingToCheckout","validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateProceedingToConfirmationPage(){
        QA_Confirmation confirm=new QA_CheckOut(driver)
                .fillForm(jsonReader.getJsonData("CheckOutForm.FirstName"),
                         jsonReader.getJsonData("CheckOutForm.MiddleName"),
                         jsonReader.getJsonData("CheckOutForm.LastName"),
                         jsonReader.getJsonData("CheckOutForm.Email"),
                         jsonReader.getJsonData("CheckOutForm.PhoneNumber"),
                         jsonReader.getJsonData("CheckOutForm.Address"),
                         jsonReader.getJsonData("CheckOutForm.State"),
                         jsonReader.getJsonData("CheckOutForm.City"),
                         jsonReader.getJsonData("CheckOutForm.PinCode"))
                .clickOnContinueBtn();
        Assertions.assertEqual(confirm.getPageTitle(),jsonReader.getJsonData("ConfirmationExpectedTitle"),"Error in Proceeding to Checkout");
    }
    @AfterClass
    public void tearDown(){
       driver.quit();
    }
}
