package selenium.selenium_scenario;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.pageobjects.LoginPage;

public class CheckoutTest {
static WebDriver driver;

    @BeforeClass
    public static void setup(){
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

    }

    @Test
    public void LoginWithValidCredentials(){
        //Insert valid credentials
        /*driver.findElement(By.id("userEmail")).sendKeys("testqa.wrc@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("@Dmin123");*/
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginApplication("testqa.wrc@gmail.com", "@Dmin123");

        driver.findElement(By.id("login")).click();
        
        String homepage = driver.findElement(By.xpath("//div[@class = 'left mt-1']/p")).getText();
        Assert.assertEquals(homepage, "Automation Practice", "Home page text doesn't match!");
         
    }

    @Test(dependsOnMethods="LoginWithValidCredentials")
    public void CheckoutScenarioTest() throws InterruptedException{
        String productName = "ZARA COAT 3";
        //Implicity wait for elements to be present
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        //Explicity wait for elements to be present
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'left mt-1']/p")));
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class = 'left mt-1']/p")));
    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    
        productToSelect.findElement(By.cssSelector(".card-body button:last-of-type")).click();
    
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //Scenario Cart Page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));

        Assert.assertTrue(isProductInCart,"Product not found in cart!");

        WebElement checkoutButton = driver.findElement(By.cssSelector(".totalRow button"));
        //Scroll sampai button checkout terlihat
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);
        
        //Tunggu sampai element bisa di click
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        Thread.sleep(1000);

        //Scenario Select Address
        Actions action = new Actions(driver);

        action.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"ind").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        String address = "Indonesia";
        List<WebElement> countries = driver.findElements(By.xpath("//span[@class='ng-star-inserted']"));
        WebElement countryToSelect = countries.stream().filter(country -> country.getText().equalsIgnoreCase(address)).findFirst().orElse(null);

        countryToSelect.click();
       
        WebElement submitButton = driver.findElement(By.cssSelector(".action__submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        
        //Tunggu sampai element bisa di click
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        

        //Scenario order confirmation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary")));
        String confirmationMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmationMsg.contains("THANKYOU FOR THE ORDER"),"Order confirmation message not found!");

        Thread.sleep(1000);
    }

    @AfterClass
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
