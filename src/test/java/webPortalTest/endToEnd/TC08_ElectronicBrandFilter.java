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
@Feature("UI Product Management")
@Story("Brand Filter")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC08_ElectronicBrandFilter {
    private WebDriver driver;
    private JsonReader jsonReader;
    private IQA_ProductPage product;
    private ProductPageAssertions Assertions;

    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("ElectronicFilter");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new ProductPageAssertions();
    }

    @Description("validate Login to the application")
    @Test
    public void validatePerformLogin(){
        QA_Shop shop=new QA_Login(driver)
                .performLogin(jsonReader.getJsonData("ElectronicLogin.username"),
                              jsonReader.getJsonData("ElectronicLogin.password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
    }
    @Description("validate Navigation to Certain Category page")
    @Test(dependsOnMethods = "validatePerformLogin")
    public void validateNavigatingToCertainCategory(){
        product=new QA_Shop(driver)
                .clickCategory(jsonReader.getJsonData("ElectronicCategory"));
        Assertions.assertTrue(product.isProductPageLoaded(),"Error in Product Page");
    }
    @Description("validate Brand Filter is working Properly")
    @Test(dependsOnMethods = {"validatePerformLogin","validateNavigatingToCertainCategory"})
    public void validateProductFilters_TC() {
        product.selectBrandFilter(jsonReader.getJsonData("BrandFilter"));
        Assertions.validateProductsBrand(product, jsonReader.getJsonData("BrandFilter"));
    }
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
