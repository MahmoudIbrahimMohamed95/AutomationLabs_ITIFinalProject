package webPortalTest.endToEnd;
import assertions.Assertions;
import businessPages.QA_Login;
import businessPages.QA_Shop;
import driverFactory.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("QA-Automation Labs")
@Feature("UI Authentication Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class TC01_Login {
    private WebDriver driver;
    private JsonReader jsonReader;
    private QA_Login login;
    private Assertions Assertions;

    @BeforeClass
    public void PreSetup(){
        jsonReader = new JsonReader("Login");
    }
    @BeforeMethod
    public void setup(){
        driver = WebDriverFactory.initDriver();
        driver.get("https://shop.qaautomationlabs.com/");
        Assertions=new Assertions();

        //JsonSystemLoader.loadFromFile("QALogin-testData");
    }
    @Description("login using valid credentials")
    @Test(priority = 3)
    public void validLogin_TC(){
    QA_Shop shop=new QA_Login(driver).
                     performLogin(jsonReader.getJsonData("valid.username"),

                                  jsonReader.getJsonData("valid.password"));
    Assertions.assertTrue(shop.isShopPageLoaded(), "ShopPageLoaded");

    }
    @Description("invalid mail and password")
    @Test(priority = 1)
    public void invalidUserLogin_TC(){
        login=new QA_Login(driver).
                  invalidLogin(jsonReader.getJsonData("invaliduser.username"),
                               jsonReader.getJsonData("invaliduser.password"));
        Assertions.assertEqual(login.getInvalidUserError(), jsonReader.getJsonData("invaliduser.ExpectedError"),"Unexpected Text");
    }
    @Description("invalid password login")
    @Test(priority = 1)
    public void invalidPasswordLogin_TC(){
        login=new QA_Login(driver).
                   invalidLogin(jsonReader.getJsonData("invalidpass.username"),
                                jsonReader.getJsonData("invalidpass.password"));
        Assertions.assertEqual(login.getInvalidPasswordError(), jsonReader.getJsonData("invalidpass.ExpectedError"),"Unexpected Text");
    }
    @Description("empty password login")
    @Test(priority = 2)
    public void emptyPasswordLogin_TC(){
        login=new QA_Login(driver).
                invalidLogin(jsonReader.getJsonData("emptyPass.username"),
                             jsonReader.getJsonData("emptyPass.password"));
        Assertions.assertEqual(login.getEmptyPasswordError(), jsonReader.getJsonData("emptyPass.ExpectedError"),"Unexpected Text");
    }
    @Description("empty email login")
    @Test(priority = 2)
    public void emptyMailLogin_TC(){
        login=new QA_Login(driver).
                invalidLogin(jsonReader.getJsonData("emptyMail.username"),
                             jsonReader.getJsonData("emptyMail.password"));
        Assertions.assertEqual(login.getEmptyMailError(), jsonReader.getJsonData("emptyMail.ExpectedError"),"Unexpected Text");
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
