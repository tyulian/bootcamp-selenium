package selenium.locator;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FlightLocator {
    static WebDriver driver;

    @BeforeMethod
    public static void setup() throws InterruptedException{
         //Setup webdriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/trisni.yuliana/Downloads/Bootcamp/chromedriver.exe");
    
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        Thread.sleep(5000); 
    }

    @Test(priority = 1)
    public void testFlightLocator() throws InterruptedException{
        System.out.println("Flight locator test is running");

        //Static Dropdown
        WebElement dpCurrency = driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
        dpCurrency.click();
        //select[@id = *ctl00_mainContent_DropDownListCurrency*]/option[@value = "AED"]
        Select staticDp = new Select(dpCurrency);
        
        System.out.println("All options: " + staticDp.getAllSelectedOptions().size());
        System.out.println("First selected option: " + staticDp.getFirstSelectedOption().getText());
        System.out.println("All options: " + staticDp.getOptions().size());

        staticDp.selectByVisibleText("USD");


        // Dynamic Dropdown
        driver.findElement(By.id("divpaxinfo")).click();

        //Increase number of adults
        //driver.findElement(By.id("hrefIncAdt")).click();

        for(int i = 0; i < 3; i++){
            driver.findElement(By.id("hrefIncAdt")).click();
        }
        
        for(int i = 0; i < 2; i++){
            driver.findElement(By.id("hrefIncChd")).click();
        }
        
        for(int i = 0; i < 0; i++){
            driver.findElement(By.id("hrefIncInf")).click();
        }



        driver.findElement(By.id("btnclosepaxoption")).click();

        //Country dropdown
        //div[@id="dropdownGroup1"]/div[@class="dropdownDiv"]/ul[1]/li[11]
        driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
        List<WebElement> optionOriginCountry = driver.findElements(By.xpath("//div[@id='dropdownGroup1']/div[@class='dropdownDiv']/ul[1]/li"));

        for (WebElement originCountry:optionOriginCountry){
        System.out.println("Optionsnya adalah: " + originCountry.getText());
            if (originCountry.getText().equalsIgnoreCase("Bhopal (BHO)")){
                //System.out.println("Optionsnya adalah: " + originCountry.getText());  
                originCountry.click();
                break;
            }
        }

        driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).click();
        List<WebElement> optionDestinationCountry = driver.findElements(By.xpath("//div[@id='dropdownGroup1']/div[@class='dropdownDiv']/ul[1]/li"));

        for (WebElement destinationCountry:optionDestinationCountry){
        System.out.println("Optionsnya adalah: " + destinationCountry.getText());
            if (destinationCountry.getText().equalsIgnoreCase("Chennai (MAA)")){
                //System.out.println("Optionsnya adalah: " + destinationCountry.getText());  
                destinationCountry.click();
                break;
            }
        }
        Thread.sleep(3000); 

        

    }

    @Test(priority = 2)
    public void handleSuggestion() throws InterruptedException{
        //Id
        driver.findElement(By.xpath("//input[@id='autosuggest']")).sendKeys("ind");

        //ByLooping
        List<WebElement> suggestCountry = driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));

        System.out.println("Total country suggestion: " + suggestCountry.size());
        for(WebElement element:suggestCountry){
            System.out.println("Country : " + element.getText());
            if(element.getText().equalsIgnoreCase("Indonesia")){
                System.out.println("Found country : " + element.getText());
                element.click();
                break;
            }
        }    
        Thread.sleep(1000); 

    }

    @Test(priority = 3)
    public void handleRadioCheckbox(){
        //radiobutton
        driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();
        /*WebElement radioButton = driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1"));
        if(radioButton.isSelected()){
            radioButton.click();
        }*/

        //checkbox
        driver.findElement(By.id("ctl00_mainContent_chk_IndArm")).click();
    }


    @AfterTest
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
