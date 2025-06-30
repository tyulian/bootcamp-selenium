package selenium.selenium_pom;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.base.BaseTest;
import com.demo.saucedemo_objects.CartObjects;
import com.demo.saucedemo_objects.CompleteObjects;
import com.demo.saucedemo_objects.DashboardObjects;
import com.demo.saucedemo_objects.InformationObjects;
import com.demo.saucedemo_objects.LoginObjects;
import com.demo.saucedemo_objects.OverviewObjects;

public class Saucedemo_Checkout extends BaseTest {
    LoginObjects loginObjects;
    DashboardObjects dashboardObjects;
    CartObjects cartObjects;
    InformationObjects informationObjects;
    OverviewObjects overviewObjects;
    CompleteObjects completeObjects;

    @BeforeClass
    public void setup(){
         //Setup webdriver
        super.setUp();
        loginObjects = new LoginObjects(driver);
        dashboardObjects = new DashboardObjects(driver);
        cartObjects = new CartObjects(driver);
        informationObjects = new InformationObjects(driver);
        overviewObjects = new OverviewObjects(driver);
        completeObjects = new CompleteObjects(driver);
    }

    @Test(priority=1)
    public void LoginWithValidCredentials(){
        loginObjects.loginApplication("standard_user","secret_sauce");
        Assert.assertEquals(dashboardObjects.getHomePageText(), "Products", "Home page text doesn't match!");
    }

    @Test(priority=2, dependsOnMethods="LoginWithValidCredentials")
    public void AddToCart(){
        String productName = "Sauce Labs Bike Light";
        dashboardObjects.getProductByName(productName);
        dashboardObjects.addToCart(productName);
        
        //Add product to cart
        dashboardObjects.clickOnCart();

        //Verify product on the cart
        boolean isProductInCart = cartObjects.verifyProductOnCart(productName);
        Assert.assertTrue(isProductInCart,"Product not found in cart!");

    }

    @Test(priority=3, dependsOnMethods="AddToCart")
    public void CheckoutProduct(){
        String productName = "Sauce Labs Bike Light";
        String headerInfoPage = "Checkout: Your Information";
        String headerOverviewPage = "Checkout: Overview";
        String headerCompletePage = "Checkout: Complete!";
        String completeMsg = "Thank you for your order!";
        
        cartObjects.goToCheckoutProduct();
        
        //Verify Information Page
        String actualHeaderInfoPage = informationObjects.getInformationPageHeader();
        Assert.assertEquals(actualHeaderInfoPage, headerInfoPage,"Header page does not match!");

        informationObjects.addInformation("Trisni", "Yuliana", "40111");
        
        //Verify Overview Page
        String actualHeaderOverviewPage = overviewObjects.getOverviewPageHeader();
        Assert.assertEquals(actualHeaderOverviewPage, headerOverviewPage, "Header page does not match!");
    
        boolean isProductInCart = cartObjects.verifyProductOnCart(productName);
        Assert.assertTrue(isProductInCart,"Product not found in cart!");

        overviewObjects.goToFinishCheckout();

        //Verify Complete Page
        String actualHeaderCompletePage = completeObjects.getCompletePageHeader();
        Assert.assertEquals(actualHeaderCompletePage, headerCompletePage,"Header page does not match!");

        String actualCompleteMsg = completeObjects.getCompleteMsg();
        Assert.assertEquals(actualCompleteMsg, completeMsg, "Complete message page does not match!");

        completeObjects.goToHome();

    }

    @Test(priority=4, dependsOnMethods="LoginWithValidCredentials")
    public void AddMultipleProducts(){

        String[] productNames = {
            "Sauce Labs Backpack",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket"
        };

        for (String productName : productNames){
            dashboardObjects.getProductByName(productName);
            dashboardObjects.addToCart(productName);
        }

        //Add product to cart
        dashboardObjects.clickOnCart();

         for (String expectedProduct : productNames) {  
            //Verify product on the cart
            boolean isProductInCart = cartObjects.verifyProductOnCart(expectedProduct);
            Assert.assertTrue(isProductInCart,"Product not found in cart!");
         }
    }

    @Test(priority=6, dependsOnMethods="AddMultipleProducts")
    public void RemoveProduct(){
        String productName = "Sauce Labs Fleece Jacket";
        cartObjects.removeProduct(productName);
    }

    @Test(priority=7, dependsOnMethods="RemoveProduct")
    public void CheckoutMultipleProducts(){

        String[] productNames = {
            "Sauce Labs Backpack",
            "Sauce Labs Bolt T-Shirt"
        };
        
        String headerInfoPage = "Checkout: Your Information";
        String headerOverviewPage = "Checkout: Overview";
        String headerCompletePage = "Checkout: Complete!";
        String completeMsg = "Thank you for your order!";

        cartObjects.goToCheckoutProduct();

        //Verify Information Page
        String actualHeaderInfoPage = informationObjects.getInformationPageHeader();
        Assert.assertEquals(actualHeaderInfoPage, headerInfoPage,"Header page does not match!");

        informationObjects.addInformation("Trisni", "Yuliana", "40111");
        
        //Verify Overview Page
        String actualHeaderOverviewPage = overviewObjects.getOverviewPageHeader();
        Assert.assertEquals(actualHeaderOverviewPage, headerOverviewPage, "Header page does not match!");
    
        for (String expectedProduct : productNames) {
            boolean isProductInCart = cartObjects.verifyProductOnCart(expectedProduct);
            Assert.assertTrue(isProductInCart,"Product not found in cart!");
        }  
        
        overviewObjects.goToFinishCheckout();

        //Verify Complete Page
        String actualHeaderCompletePage = completeObjects.getCompletePageHeader();
        Assert.assertEquals(actualHeaderCompletePage, headerCompletePage,"Header page does not match!");

        String actualCompleteMsg = completeObjects.getCompleteMsg();
        Assert.assertEquals(actualCompleteMsg, completeMsg, "Complete message page does not match!");

        completeObjects.goToHome();

    }

    @Test(dependsOnMethods="LoginWithValidCredentials")
    public void FilterProduct(){
        String option = "Price (low to high)";
        dashboardObjects.filterProduct(option);

        switch (option) {
        case "Name (A to Z)" -> {
            List<String> actualNames = dashboardObjects.getProductNameList();
            List<String> expectedNames = dashboardObjects.sortProductNamesAsc(actualNames);
            Assert.assertEquals(actualNames, expectedNames, "Product names are not sorted A to Z!");
        }
        case "Name (Z to A)" -> {
            List<String> actualNames = dashboardObjects.getProductNameList();
            List<String> expectedNames = dashboardObjects.sortProductNamesDesc(actualNames);
            Assert.assertEquals(actualNames, expectedNames, "Product names are not sorted Z to A!");
        }
        case  "Price (low to high)" -> {
            List<Double> actualPrices = dashboardObjects.getProductPrices();
            List<Double> expectedPrices = dashboardObjects.sortPricesLowToHigh(actualPrices);
            Assert.assertEquals(actualPrices, expectedPrices, "Filter Low to High not applied");
        }
        case "Price (high to low)" -> {
            List<Double> actualPrices = dashboardObjects.getProductPrices();
            List<Double> expectedPrices = dashboardObjects.sortPricesHighToLow(actualPrices);
            Assert.assertEquals(actualPrices, expectedPrices, "Filter High to Low to not applied");
        }    
        }     
    }
    @AfterClass
    public void tearDown(){
        super.tearDown();
    }
}