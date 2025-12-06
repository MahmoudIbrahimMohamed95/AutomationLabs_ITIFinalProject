package webPortalTest.endToEnd;

import assertions.ProductPageAssertions;
import businessPages.IQA_ProductPage;
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
@Feature("UI Product Filtering")
@Story("Filter by Size")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC06_ProductSizeFilter {
    private WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private ProductPageAssertions Assertions;

    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("ProductPage");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new ProductPageAssertions();
    }
    @Description("Login to the Application")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                .performLogin(jsonReader.getJsonData("ProductLogin.username"),
                        jsonReader.getJsonData("ProductLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("Navigating to Certain Category")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
                .clickCategory(jsonReader.getJsonData("ProductCategory_Size"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("Filter by Size")
    @Test(dependsOnMethods = {"validateNavigatingToCertainCategory","validatePerformLogin"})
    public void validateProductSizeFilter_TC() {
        product.selectSizeFilter(jsonReader.getJsonData("ProductSizeFilter"));
        Assertions.validateProductsSize(product, jsonReader.getJsonData("ProductExpectedSize"));
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
