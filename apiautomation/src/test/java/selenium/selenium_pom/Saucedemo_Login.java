package selenium.selenium_pom;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demo.base.BaseTest;
import com.demo.saucedemo_objects.DashboardObjects;
import com.demo.saucedemo_objects.LoginObjects;

public class Saucedemo_Login extends BaseTest{
    LoginObjects loginObjects;
    DashboardObjects dashboardObjects;

    @BeforeMethod
    public void setup(){
        super.setUp();
        loginObjects = new LoginObjects(driver);
        dashboardObjects = new DashboardObjects(driver);
    }

    @Test(priority = 1)
    public void LoginWithValidCredentials(){
        loginObjects.loginApplication("standard_user","secret_sauce");
        Assert.assertEquals(dashboardObjects.getHomePageText(), "Products", "Home page text doesn't match!");
    }
    
    @Test(priority = 2, dataProvider="InvalidLoginData")
    public void InvalidLogin(String username, String password, String errorMsg){
        loginObjects.loginApplication(username,password);

        //Validate error message     
        if(loginObjects.isErrorMsgVisible()){
            String loginErrorMsg = loginObjects.getErrorMsg();
            Assert.assertEquals(loginErrorMsg, errorMsg, "Login error message doesn't match!");
        }
        
    }

    @DataProvider(name="InvalidLoginData")
    public Object InvalidLoginData(){
        return new Object[][]{
            {"standard_user", "", "*Password is required"},
            {"", "secret_sauce", "*Username is required"},
            {"standard", "admin123", "*Username and password do not match any user in this service"},
            {"locked_out_user", "secret_sauce", "*Sorry, this user has been locked out."},
        };
        
    }

    public Boolean isElementPresent(By by){
        try{
            driver.findElement(by);
            return true;        
        } catch (Exception e){
            return false;
        }

    }

    @Test(priority=3)
    public void Logout(){
        LoginWithValidCredentials();
        loginObjects.logoutApplication();
    }

    @AfterMethod
    public void tearDown(){
        super.tearDown();
    }
}
