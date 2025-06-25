package selenium.locator;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Alert {

    static WebDriver driver;
    @Test
    public void alertTest() throws InterruptedException{
        //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        Thread.sleep(1000); 

        driver.findElement(By.id("name")).sendKeys("Test Alert");
        driver.findElement(By.id("alertbtn")).click();

        //handle alert
        System.out.println("Alert text: " + driver.switchTo().alert().getText());
        Thread.sleep(1000);
        driver.switchTo().alert().accept();

        driver.quit();
    }
}
