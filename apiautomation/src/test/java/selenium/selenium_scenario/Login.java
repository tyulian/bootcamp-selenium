package selenium.selenium_scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Login {
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
        driver.findElement(By.id("userEmail")).sendKeys("testqa.wrc@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("@Dmin123");
        driver.findElement(By.id("login")).click();
        
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
 
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();

        //Validate error message        
        if(isElementPresent(By.xpath("//input[@id='userEmail']/following-sibling::div//div[@class='ng-star-inserted']"))){
            String emailErrorMsg = driver.findElement(By.xpath("//input[@id='userEmail']/following-sibling::div//div[@class='ng-star-inserted']")).getText();
            Assert.assertEquals(emailErrorMsg, emailError, "Email error message doesn't match!");
        }
        if(isElementPresent(By.xpath("//input[@id='userPassword']/following-sibling::div//div[@class='ng-star-inserted']"))){
            String passwordErrorMsg = driver.findElement(By.xpath("//input[@id='userPassword']/following-sibling::div//div[@class='ng-star-inserted']")).getText();
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
