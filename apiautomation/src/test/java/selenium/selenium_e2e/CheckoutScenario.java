package selenium.selenium_e2e;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutScenario {
   static WebDriver driver;

    @BeforeClass
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

    /*
     * 1. Login using valid credentials
     * 2. Add product to cart
     * 3. Checkout product
     * 4. Verify Success checkout product
     */

    @Test(priority=1)
    public void Login(){
        //Insert valid credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        String homepage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(homepage, "Products", "Home page text doesn't match!");
         
    }

    @Test(priority=2, dependsOnMethods="Login")
    public void AddToCart(){
        String productName = "Sauce Labs Bike Light";
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
       
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(By.className("inventory_item_name")).getText().equals(productName)).findFirst().orElse(null);
        
        // Validasi produk ditemukan
        if (productToSelect == null) {
            Assert.fail("Product with name '" + productName + "' not found.");
        }

        // Scroll ke tombol add to cart dari produk yang sesuai
        WebElement addToCartButton = productToSelect.findElement(By.cssSelector(".pricebar button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);

        // Klik tombol add to cart
        addToCartButton.click();

        //Verify Cart
        driver.findElement(By.className("shopping_cart_link")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        List<WebElement> cartProducts = driver.findElements(By.className("inventory_item_name"));
        boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));

        Assert.assertTrue(isProductInCart,"Product not found in cart!");

    }

    @Test(priority=3, dependsOnMethods="AddToCart")
    public void CheckoutProduct() throws InterruptedException{
       
        String productName = "Sauce Labs Bike Light";

        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        checkoutBtn.click();

        //Verify information page
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
              
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String infoPage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(infoPage, "Checkout: Your Information", "Header page does not match!");

        driver.findElement(By.id("first-name")).sendKeys("Trisni");
        driver.findElement(By.id("last-name")).sendKeys("Yuliana");
        driver.findElement(By.id("postal-code")).sendKeys("40111");

        // Scroll ke tombol continue
        WebElement continueBtn = driver.findElement(By.id("continue"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);

        // Klik tombol continue
        continueBtn.click();

        //Verify overview page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String overviewPage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(overviewPage, "Checkout: Overview", "Header page does not match!");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        List<WebElement> cartProducts = driver.findElements(By.className("inventory_item_name"));
        
        boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));
        Assert.assertTrue(isProductInCart,"Product not found in cart!");
        
        // Scroll ke tombol finish
        WebElement finishBtn = driver.findElement(By.id("finish"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finishBtn);

        finishBtn.click();

        //Verify complete page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String completePage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(completePage, "Checkout: Complete!", "Header page does not match!");
        
        String thankOrderMsg = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(thankOrderMsg, "Thank you for your order!", "Message does not match!");

        Thread.sleep(1000);

        // Scroll ke tombol finish
        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", backHomeBtn);

        // Klik tombol continue
        backHomeBtn.click();
    }

    @Test(priority=4, dependsOnMethods="Login")
    public void AddMultipleProducts(){

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        String[] productNames = {
            "Sauce Labs Backpack",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket"
        };
       
        for (String productName : productNames){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));
            List<WebElement> products = driver.findElements(By.className("inventory_item"));
            
            WebElement productToSelect = products.stream().filter(prod -> prod.findElement(By.className("inventory_item_name")).getText().equals(productName)).findFirst().orElse(null);
            
            // Validasi produk ditemukan
            if (productToSelect == null) {
                Assert.fail("Product with name '" + productName + "' not found.");
            }

            // Scroll ke tombol add to cart dari produk yang sesuai
            WebElement addToCartButton = productToSelect.findElement(By.cssSelector(".pricebar button"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);

            // Klik tombol add to cart
            addToCartButton.click();
        }     

        driver.findElement(By.className("shopping_cart_link")).click();   

        //Verify Cart     
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        List<WebElement> cartProducts = driver.findElements(By.className("inventory_item_name"));
        
        for (String expectedProduct : productNames) {
            boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(expectedProduct));
            Assert.assertTrue(isProductInCart,"Product not found in cart!");
        }          

    }
    
    @Test(priority=6, dependsOnMethods="AddMultipleProducts")
    public void RemoveProduct(){
        
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String productName = "Sauce Labs Fleece Jacket";
        List<WebElement> products = driver.findElements(By.className("cart_item"));           
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(By.className("inventory_item_name")).getText().equals(productName)).findFirst().orElse(null);
        
        WebElement removeBtn = productToSelect.findElement(By.cssSelector(".cart_item button"));    
        removeBtn.click();

        //Verify item berhasil dihapus
        By productLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        boolean deletedItem = wait.until(ExpectedConditions.invisibilityOfElementLocated(productLocator));

        Assert.assertTrue(deletedItem, "Product '" + productName + "' still visible in the cart after removal!");
    }

    @Test(priority=7, dependsOnMethods="AddMultipleProducts")
    public void CheckoutMultipleProducts(){

        String[] productNames = {
            "Sauce Labs Backpack",
            "Sauce Labs Bolt T-Shirt"
        };

        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        checkoutBtn.click();

        //Verify information page
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
              
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String infoPage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(infoPage, "Checkout: Your Information", "Header page does not match!");

        driver.findElement(By.id("first-name")).sendKeys("Trisni");
        driver.findElement(By.id("last-name")).sendKeys("Yuliana");
        driver.findElement(By.id("postal-code")).sendKeys("40111");

        // Scroll ke tombol continue
        WebElement continueBtn = driver.findElement(By.id("continue"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);

        // Klik tombol continue
        continueBtn.click();

        //Verify overview page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String overviewPage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(overviewPage, "Checkout: Overview", "Header page does not match!");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        List<WebElement> cartProducts = driver.findElements(By.className("inventory_item_name"));
        
        for (String expectedProduct : productNames) {
            boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(expectedProduct));
            Assert.assertTrue(isProductInCart,"Product not found in cart!");
        }   

        // Scroll ke tombol finish
        WebElement finishBtn = driver.findElement(By.id("finish"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finishBtn);

        finishBtn.click();

        //Verify complete page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class = 'title']")));
        String completePage = driver.findElement(By.xpath("//span[@class = 'title']")).getText();
        Assert.assertEquals(completePage, "Checkout: Complete!", "Header page does not match!");
        
        String thankOrderMsg = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(thankOrderMsg, "Thank you for your order!", "Message does not match!");
      
        // Scroll ke tombol finish
        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", backHomeBtn);

        // Klik tombol continue
        backHomeBtn.click();
    }

    @Test(dependsOnMethods="Login")
    public void FilterProduct() throws InterruptedException{
        WebElement filterProduct = driver.findElement(By.className("product_sort_container"));
        filterProduct.click();
        Select optionFilter = new Select(filterProduct);
        optionFilter.selectByVisibleText("Price (low to high)");

        //Verify Result filter
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));
        
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = priceElements.stream()
        .map(price -> Double.parseDouble(price.getText().replace("$", "")))
        .collect(Collectors.toList());

        //Product yang sudah di filter
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        //Verify product sudah difilter dari low to high
        Assert.assertEquals(actualPrices, expectedPrices, "Filter Low to high not applied");

        Thread.sleep(1000);

    }

     @AfterClass
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
}
