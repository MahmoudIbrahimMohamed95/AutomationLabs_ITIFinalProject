package webPortalTest.endToEnd;
import assertions.Assertions;
import businessPages.IQA_ProductPage;
import businessPages.QA_Login;
import businessPages.QA_Shop;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import readers.JsonReader;

@Epic("QA-Automation Labs")
@Feature("UI Shop Management")
@Story("Category Selection")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC02_Shop {
    private WebDriver driver;
    private JsonReader jsonReader;
    private Assertions Assertions;

    @BeforeClass
    public void setup(){
        jsonReader = new JsonReader("Shop");
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions= new Assertions();
    }

    @Description("Login to Shop")
    @Test
    public void validateCategoriesPresence_TC(){
        QA_Shop shop=new QA_Login(driver)
                     .performLogin(jsonReader.getJsonData("Shop_Username"),
                                   jsonReader.getJsonData("Shop_Password"));
        Assertions.assertTrue(shop.isShopPageLoaded(),"Shop page is not loaded")
                  .assertEqual(shop.getCategory(jsonReader.getJsonData("Shop_Category")),
                                                jsonReader.getJsonData("Expected_Shop"),"Category is not loaded");
    }
    @Description("Navigate to Product Page")
    @Test(dependsOnMethods = "validateCategoriesPresence_TC")
    public void validateNavigationToProductPage_TC(){
        IQA_ProductPage prod =new QA_Shop(driver)
                              .clickCategory(jsonReader.getJsonData("Shop_CategoryBtn"));
        Assertions.assertTrue(prod.isProductPageLoaded(),"Product page is not loaded");
    }
    @AfterClass
    public void teardown(){
       driver.quit();
    }
}
