package selenium.selenium_scenario;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.pageobjects.CartPage;
import com.demo.pageobjects.ConfirmationPage;
import com.demo.pageobjects.DashboardPage;
import com.demo.pageobjects.LoginPage;
import com.demo.pageobjects.OrderPage;

public class CheckoutTestImplPOM {
WebDriver driver;
LoginPage loginPage;
DashboardPage dashboardPage;
CartPage cartPage;
OrderPage orderPage;
ConfirmationPage confirmPage;

    @BeforeClass
    public void setup(){
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        cartPage = new CartPage(driver);
        orderPage = new OrderPage(driver);
        confirmPage = new ConfirmationPage(driver);
    }

    @Test
    public void LoginWithValidCredentials(){
        loginPage.loginApplication("testqa.wrc@gmail.com", "@Dmin123");
        Assert.assertEquals(dashboardPage.getHomePageText(), "Automation Practice", "Home page text doesn't match!");
    }

    @Test(dependsOnMethods="LoginWithValidCredentials")
    public void CheckoutScenarioTest() throws InterruptedException{
        String productName = "ZARA COAT 3";
        String destinationCountry = "Indonesia";
        //Dashboard 
        dashboardPage.addToCart(productName); 
        //Thread.sleep(1000);
        dashboardPage.clickOnCart();
        
        //Scenario Cart Page
        boolean isProductInCart = cartPage.verifyCheckoutProduct(productName);
        Assert.assertTrue(isProductInCart,"Product not found in cart!");
        cartPage.goToCheckoutProduct();

        //Order Page
        orderPage.selectCountry(destinationCountry);
        orderPage.submitOrder();

        //Scenario order confirmation
        Assert.assertTrue(confirmPage.getConfirmationMsg().contains("THANKYOU FOR THE ORDER"),"Order confirmation message not found!");
        Thread.sleep(1000);
    }

    @AfterClass
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
