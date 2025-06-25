package selenium.selenium_e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginLogoutScenario {
    static WebDriver driver;

    @BeforeMethod
    public static void setup(){
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @Test
    public void LoginWithValidCredentials(){
        //Insert valid credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        String homepage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(homepage, "Products", "Home page text doesn't match!");
         
    }

   @Test(dataProvider="InvalidLoginData")
    public void InvalidLogin(String username, String password, String errorMsg){
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        
        //Validate error message        
        if(isElementPresent(By.cssSelector(".h3"))){
            String loginErrorMsg = driver.findElement(By.cssSelector(".h3")).getText();
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
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        
    }

    @AfterMethod
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
