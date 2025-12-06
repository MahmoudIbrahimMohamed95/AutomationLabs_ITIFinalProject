package webPortalTest.endToEnd;

import assertions.CartPageAssertions;
import assertions.CheckoutPageAssertions;
import businessPages.*;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("QA-Automation Labs")
@Feature("UI Checkout Management")
@Story("Field Validation – Empty Fields")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC14_CheckoutWithEmptyField {
    private WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private CheckoutPageAssertions Assertions;
    private CartPageAssertions CartAssertions;
    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("CheckOutEmptyField");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new CheckoutPageAssertions();
        CartAssertions=new CartPageAssertions();
    }
    @Description("validate perform login")
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
    @Description("validate checkout without entering delivery address")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin","validateAddingProductToCart"})
    public void checkoutLeavingEmptyAddress(){
        QA_CheckOut checkout=new QA_Cart(driver)
                .clickOnProceedToCheckout()
                .fillForm(jsonReader.getJsonData("CheckOutForm.FirstName"),
                        jsonReader.getJsonData("CheckOutForm.MiddleName"),
                        jsonReader.getJsonData("CheckOutForm.LastName"),
                        jsonReader.getJsonData("CheckOutForm.Email"),
                        jsonReader.getJsonData("CheckOutForm.PhoneNumber"),
                        jsonReader.getJsonData("CheckOutForm.Address"),
                        jsonReader.getJsonData("CheckOutForm.State"),
                        jsonReader.getJsonData("CheckOutForm.City"),
                        jsonReader.getJsonData("CheckOutForm.PinCode"))
                .clickContinueBtn();
        Assertions.assertEqual(checkout.getAddressError(),jsonReader.getJsonData("CheckOutExpectedError"),"Error in Proceeding to Checkout");
    }
    @AfterClass
    public void tearDown(){
      driver.quit();
    }
}
