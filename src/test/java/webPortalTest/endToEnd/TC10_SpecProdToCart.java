package webPortalTest.endToEnd;
import assertions.CartPageAssertions;
import businessPages.*;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("QA-Automation Labs")
@Feature("UI Cart Management")
@Story("Add To Cart - from Certain Product PAge")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC10_SpecProdToCart {
    private  WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private CartPageAssertions  Assertions;

    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("ProductDetails");
        //JsonSystemLoader.loadFromFile("QALogin-testData");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new CartPageAssertions();
    }
    @Description("validate Login to the application")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
               .performLogin(jsonReader.getJsonData("CertainProductLogin.username"),
                              jsonReader.getJsonData("CertainProductLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("validate Navigating to Certain Category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
               .clickCategory(jsonReader.getJsonData("CertainProductCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("validate Certain Product Details")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateCertainProductDetails() {
        QA_ProductDetails certainProduct=product.clickOnCertainProduct(jsonReader.getJsonData("CertainProductDetails.Name"))
                                                .clickOnAddToCartButton();
        Assertions.assertEqual(certainProduct.getProductName( jsonReader.getJsonData("CertainProductDetails.Name")),
                                                              jsonReader.getJsonData("CertainProductDetails.ExpectedName"),"notAs")
                  .assertEqual(certainProduct.getProductPrice(jsonReader.getJsonData("CertainProductDetails.Name")),
                                                              jsonReader.getJsonData("CertainProductDetails.ExpectedPrice"),"notAs")
                  .assertTrue(certainProduct.isCartCounterIncremented(),"Error Not Incremented");
    }
    @Description("validate Navigation to Cart Page")
    @Test(dependsOnMethods = {"validateCertainProductDetails","validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateNavigatingToCartPage_TC()
    {
        QA_Cart cart=new QA_ProductDetails(driver)
                 .clickOnCartIcon();
        Assertions.validateCertainCartProductDetails(cart
                        ,jsonReader.getJsonData("CertainProductDetails.Name")
                        ,jsonReader.getJsonData("CertainProductDetails.ExpectedName")
                        ,jsonReader.getJsonData("CertainProductDetails.ExpectedPrice")
                        ,jsonReader.getJsonData("CertainProductDetails.ExpectedQty")
                        ,jsonReader.getJsonData("CertainProductDetails.ExpectedTotalPrice"))
                  .assertEqual(cart.getCartNavigationTittle(), jsonReader.getJsonData("CartExpectedMessage"), "Error Not Incremented");
    }
    @AfterClass
    public void tearDown(){
       driver.quit();
    }
}


