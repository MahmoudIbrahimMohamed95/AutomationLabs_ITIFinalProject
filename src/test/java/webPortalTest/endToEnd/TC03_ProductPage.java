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
@Story("product page process")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC03_ProductPage {
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
        @Description("validate login to the application")
        @Test
        public void validatePerformLogin(){
            QA_Shop shop=new QA_Login(driver)
                    .performLogin(jsonReader.getJsonData("ProductLogin.username"),
                            jsonReader.getJsonData("ProductLogin.password"));
            Assertions.assertTrue(shop.isShopPageLoaded(),"Error in Login");
        }
        @Description("validate Navigating to certain category")
        @Test(dependsOnMethods = "validatePerformLogin")
        public void validateProductPage(){
            product=new QA_Shop(driver)
                    .clickCategory(jsonReader.getJsonData("ProductCategory"));
            Assertions.validateCertainProductDetails( product, jsonReader.getJsonData("ProductDetails.Name")
                                        ,jsonReader.getJsonData("ProductDetails.ExpectedName")
                                        ,jsonReader.getJsonData("ProductDetails.ExpectedPrice")
                                        ,jsonReader.getJsonData("ProductDetails.ExpectedSize")
                                        ,jsonReader.getJsonData("ProductDetails.ExpectedColor")
                                        ,jsonReader.getJsonData("ProductDetails.ExpectedType"))
                      .assertTrue(product.isProductPageLoaded(),"Error in Product Page");
        }
        @AfterClass
        public void tearDown(){
            driver.quit();
        }
}
