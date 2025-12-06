package webPortalTest.endToEnd;

import assertions.CartPageAssertions;
import businessPages.IQA_ProductPage;
import businessPages.QA_Cart;
import businessPages.QA_Login;
import businessPages.QA_Shop;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;
@Epic("QA-Automation Labs")
@Feature("UI Cart Management")
@Story("Remove Product From Cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")
public class TC12_RemoveFromCart {
    private WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private QA_Cart cart;
    private CartPageAssertions Assertions;
    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("Cart");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new CartPageAssertions();
    }
    @Description("Login to the Apllication")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                .performLogin(jsonReader.getJsonData("CartLogin.username"),
                        jsonReader.getJsonData("CartLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("Navigating to Certain Category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
                .clickCategory(jsonReader.getJsonData("CartCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("Adding Product to Cart")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateAddingProductToCart(){
        cart=product.addToCart(jsonReader.getJsonData("CartProduct.Name"))
                            .clickOnCartIcon();
        Assertions.validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CartProduct.Name")
                        ,jsonReader.getJsonData("CartProduct.ExpectedName")
                        ,jsonReader.getJsonData("CartProduct.ExpectedPrice")
                        ,jsonReader.getJsonData("CartProduct.ExpectedQty")
                        ,jsonReader.getJsonData("CartProduct.ExpectedTotalPrice"))
                .assertEqual(jsonReader.getJsonData("CartProduct.ExpectedTotalPrice")
                        ,cart.getCartTotalPrice(),"Error in Calculating Total Price")
                .assertTrue(cart.isCartPageLoaded(),"Error in Cart Page");
    }
    @Description("Removing Product from Cart")
    @Test(dependsOnMethods = {"validateAddingProductToCart","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateRemovingProductFromCart() {
        cart.removeProduct(jsonReader.getJsonData("CartProduct.Name"));
        Assertions.assertEqual(cart.getCartCounter(), jsonReader.getJsonData("ExpectedCartCounter"),"Error in Navigating to CheckOut Page");
    }
    @AfterClass
    public void tearDown(){
       driver.quit();
    }
}
