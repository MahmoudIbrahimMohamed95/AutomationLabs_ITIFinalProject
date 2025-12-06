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
@Story("Add Different Categories to Cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC11_AllCategoriesToCart {
    private WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private CartPageAssertions Assertions;

    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("CategoriesCart");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new CartPageAssertions();
    }
    @Description("validate Login to the Application Using UI")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                .performLogin(jsonReader.getJsonData("CartCategoriesLogin.username"),
                              jsonReader.getJsonData("CartCategoriesLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("validate Navigating to Certain Category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
                    .clickCategory(jsonReader.getJsonData("CartCategoriesCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("validate Adding Product to Cart")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateAddingProductToCart(){
        QA_Cart cart=product.addToCart(jsonReader.getJsonData("CartProduct_Men.Name"))
                            .selectShopCategory()
                            .addToCart(jsonReader.getJsonData("CartProduct_Women.Name"))
                            .selectShopCategory()
                            .addToCart(jsonReader.getJsonData("CartProduct_Kids.Name"))
                            .selectShopCategory()
                            .addToCart(jsonReader.getJsonData("CartProduct_Electronics.Name"))
                            .clickOnCartIcon();
        Assertions.validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CartProduct_Men.Name")
                        ,jsonReader.getJsonData("CartProduct_Men.ExpectedName")
                        ,jsonReader.getJsonData("CartProduct_Men.ExpectedPrice")
                        ,jsonReader.getJsonData("CartProduct_Men.ExpectedQty")
                        ,jsonReader.getJsonData("CartProduct_Men.ExpectedTotalPrice"))
                  .validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CartProduct_Women.Name")
                        ,jsonReader.getJsonData("CartProduct_Women.ExpectedName")
                        ,jsonReader.getJsonData("CartProduct_Women.ExpectedPrice")
                        ,jsonReader.getJsonData("CartProduct_Women.ExpectedQty")
                        ,jsonReader.getJsonData("CartProduct_Women.ExpectedTotalPrice"))
                .validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CartProduct_Kids.Name")
                        ,jsonReader.getJsonData("CartProduct_Kids.ExpectedName")
                        ,jsonReader.getJsonData("CartProduct_Kids.ExpectedPrice")
                        ,jsonReader.getJsonData("CartProduct_Kids.ExpectedQty")
                        ,jsonReader.getJsonData("CartProduct_Kids.ExpectedTotalPrice"))
                .validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CartProduct_Electronics.Name")
                        ,jsonReader.getJsonData("CartProduct_Electronics.ExpectedName")
                        ,jsonReader.getJsonData("CartProduct_Electronics.ExpectedPrice")
                        ,jsonReader.getJsonData("CartProduct_Electronics.ExpectedQty")
                        ,jsonReader.getJsonData("CartProduct_Electronics.ExpectedTotalPrice"))

                .assertEqual(cart.getCartTotalPrice(),
                            jsonReader.getJsonData("ExpectedCartCategoriesTotal"),"Error in Calculating Total Price")
                .assertTrue(cart.isCartPageLoaded(),"Error in Cart Page");
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
