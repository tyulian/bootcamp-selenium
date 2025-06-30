package selenium.selenium_scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demo.pageobjects.LoginPage;

public class LoginImplPOM {
static WebDriver driver;

    @BeforeMethod
    public static void setup() throws InterruptedException{
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        Thread.sleep(5000); 
    }

    @Test(priority = 1)
    public void validCredentials(){
        //Insert valid credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginApplication("testqa.wrc@gmail.com", "@Dmin123");

        String homepage = driver.findElement(By.xpath("//div[@class = 'left mt-1']/p")).getText();
        Assert.assertEquals(homepage, "Automation Practice", "Home page text doesn't match!");
         
    }

    @Test(priority = 2, dataProvider="invalidCredentialsData")
    public void invalidCredentials(String email, String password, String emailError, String passwordError){
        /*
         * valid email empty password
         * invalid email valid password
         * invalid email invalid password
         * empty email invalid password
         */
 
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginApplication(email, password);

        /*DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.implementFilter();
        dashboardPage.inputRangePrice("50", "100");*/

        //Validate error message        
        if(loginPage.isEmailErrorMsgVisible()){
            String emailErrorMsg = loginPage.getEmailErrorMsg();
            Assert.assertEquals(emailErrorMsg, emailError, "Email error message doesn't match!");
        }
        if(loginPage.isPasswordErrorMsgVisible()){
            String passwordErrorMsg = loginPage.getPasswordErrorMsg();
            Assert.assertEquals(passwordErrorMsg, passwordError, "Password error message doesn't match!");
        }
    }

    @DataProvider(name="invalidCredentialsData")
    public Object invalidCredentialsData(){
        return new Object[][]{
            {"testqa.wrc@gmail.com", "", "", "*Password is required"},
            {"testqa", "@Dmin123", "*Enter Valid Email", ""},
            {"testqa", "admin123", "*Enter Valid Email", "*Password is required"},
            {"", "admin123", "*Email is required", "*Password is required"}
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
    
    @AfterMethod
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
