package businessPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilActions.ElementActions;

public class QA_Login {

    //attributes
    private static WebDriver driver;
    private  ElementActions actions;


    //locators
    private static final By email=By.id("email");
    private static final By password=By.id("password");
    private static final By loginBtn=By.id("loginBtn");
    private static final By loginLogo=By.className("bg-secondary");
    private static final By emailError=By.id("emailerror");
    private static final By passwordError=By.id("errorMsg");
    private static final By emptyPassword=By.id("passerror");
    private static final By emptyMail=By.id("emailerror");
    //Constructor
    public QA_Login(WebDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);

    }

    //Methods
    public QA_Shop performLogin(String userName, String pass){
        actions.type(email , userName);
        actions.type(password , pass);
        actions.click(loginBtn);
        return new QA_Shop(driver);

    }
    public QA_Login invalidLogin(String invalidUserName, String invalidPassword){
        actions.type(email , invalidUserName);
        actions.type(password , invalidPassword);
        actions.click(loginBtn);
        return this;
    }
    public String getInvalidUserError(){
        return  actions.getText(emailError);
    }

    public String getInvalidPasswordError(){
        return actions.getText(passwordError);
    }
    public String getEmptyMailError(){
        return  actions.getText(emptyMail);
    }
    public String getEmptyPasswordError(){
        return  actions.getText(emptyPassword);
    }

}
