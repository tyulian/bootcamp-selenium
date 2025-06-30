package selenium.hook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import helper.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    public static WebDriver driver;
    
    @Before
    public void setUp(){
        System.out.println(">> Menjalankan setup sebelum scenario");
        //Setup webdriver
        String browser = ConfigManager.getBrowser();
        String webUrl = ConfigManager.getWebURL();
        if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-notifications");

            driver = new ChromeDriver(options);
        }
        /*else if(browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/firefoxdriver.exe");
             driver = new FirefoxDriver();
        }*/
        driver.get(webUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @After
    public void tearDown(){
        System.out.println(">> Menjalankan teardown setelah scenario");
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver initializeDriver(){
        System.out.println(">> Return sebuah driver");
        return driver;
    }
}
