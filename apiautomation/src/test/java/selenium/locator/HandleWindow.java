package selenium.locator;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleWindow {

    static WebDriver driver;

    @BeforeMethod
    public static void setup() throws InterruptedException{
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/loginpagePractise/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        Thread.sleep(1000); 
    }

    @Test
    public void handleWindowTest() throws InterruptedException{
        driver.findElement(By.className("blinkingText")).click();

        Set<String> windowHandles = driver.getWindowHandles();
        /*
         * Akan ada dua value didalam set ini [parent window and child window]
         */

        System.out.println("Total window handles: " + windowHandles.size());
        System.out.println("Window handles: " + windowHandles);

        Iterator<String> iterator = windowHandles.iterator();
        String parentWindow = iterator.next();
        String childWindow = iterator.next();

        Thread.sleep(2000);
        driver.switchTo().window(childWindow);

        String getText = driver.findElement(By.cssSelector("h1")).getText();
        Assert.assertEquals("DOCUMENTS REQUEST", getText, "Text doesn't match!");

        Thread.sleep(2000);
        driver.switchTo().window(parentWindow);

        Thread.sleep(2000);
        driver.switchTo().window(childWindow);

        Thread.sleep(2000);
        driver.switchTo().window(parentWindow);

        driver.quit();
    }
}
