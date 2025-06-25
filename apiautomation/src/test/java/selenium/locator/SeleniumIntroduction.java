package selenium.locator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumIntroduction {
    static WebDriver driver;

    @BeforeMethod
    public static void setup() throws InterruptedException{
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        Thread.sleep(5000); 
    }

    @Test
    public void loginScenario() throws InterruptedException{       
        /*
         * Steps:
         * 1. User opens the browser and navigates to the login page
         * 2. User enters the username and password
         * 3. User clicks the login button
         * 4. User is redirected to the home page
         */

        WebElement txtUserName = driver.findElement(By.id("inputUsername"));
        txtUserName.sendKeys("testqa.wrc@gmail.com");

        WebElement txtPassword = driver.findElement(By.name("inputPassword"));
        txtPassword.sendKeys("rahulshettyacademy");

        //WebElement cbRememberName = driver.findElement(By("inputPassword"));
        //cbRememberName.click();

        WebElement btnLogin = driver.findElement(By.className("signInBtn"));
        btnLogin.click();

        //verify homepage
        String name = driver.findElement(By.xpath("//div[@class = 'login-container']/h2")).getText();
        System.out.println("Ini adalah nama user " + name);

        driver.quit();
    }
}
