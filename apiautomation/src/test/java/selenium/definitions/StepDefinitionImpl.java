package selenium.definitions;

import java.util.List;

import org.testng.Assert;

import com.demo.base.BaseTest;
import com.demo.saucedemo_objects.CartObjects;
import com.demo.saucedemo_objects.CompleteObjects;
import com.demo.saucedemo_objects.DashboardObjects;
import com.demo.saucedemo_objects.InformationObjects;
import com.demo.saucedemo_objects.LoginObjects;
import com.demo.saucedemo_objects.OverviewObjects;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import selenium.hook.Hooks;

public class StepDefinitionImpl extends BaseTest{
    LoginObjects loginObjects;
    DashboardObjects dashboardObjects;
    CartObjects cartObjects;
    InformationObjects informationObjects;
    OverviewObjects overviewObjects;
    CompleteObjects completeObjects;

    @Given("User landing to logged ecommerce")
    public void landingPage(){
        driver = Hooks.initializeDriver();

        loginObjects = new LoginObjects(driver);
        dashboardObjects = new DashboardObjects(driver);
        cartObjects = new CartObjects(driver);
        informationObjects = new InformationObjects(driver);
        overviewObjects = new OverviewObjects(driver);
        completeObjects = new CompleteObjects(driver);
    }

    @When("User input username {string} and password {string}")
    public void userLogin(String username, String password){
        loginObjects.loginApplication(username, password);
    }

    @Then("User redirect to homepage")
    public void userOnHomePage(){
        Assert.assertEquals(dashboardObjects.getHomePageText(), "Products", "Home page text doesn't match!");
    }

    @Then("Verify error message {string}")
    public void verifyErrorMsg(String errorMsg){
        if(loginObjects.isErrorMsgVisible()){
            String loginErrorMsg = loginObjects.getErrorMsg();
            Assert.assertEquals(loginErrorMsg, errorMsg, "Login error message doesn't match!");
        }
    }

    @When("Buyer add product {string} to cart")
    public void buyerAddProduct(String productName){
        dashboardObjects.getProductByName(productName);
        dashboardObjects.addToCart(productName);        
    }

    @And("Buyer redirect to cart page")
    public void buyerGoToCartPage(){
        dashboardObjects.clickOnCart();
    }

    @Then("Verify product {string} is on cart")
    public void verifyCartPage(String productName){
        boolean isProductInCart = cartObjects.verifyProductOnCart(productName);
        Assert.assertTrue(isProductInCart,"Product not found in cart!");
    }

    @When("Buyer redirect to checkout page {string}")
    public void buyerGoToCheckoutPage(String headerInfoPage){
        cartObjects.goToCheckoutProduct();
        
        //Verify Information Page
        String actualHeaderInfoPage = informationObjects.getInformationPageHeader();
        Assert.assertEquals(actualHeaderInfoPage, headerInfoPage,"Header page does not match!");

    }

    @And("Buyer input first name {string}, last name {string}, and postal code {string}")
    public void buyerAddInformation(String firstname, String lastname, String postalCode){
        informationObjects.addInformation(firstname, lastname, postalCode);        
    }
   
    @Then("Verify on overview page {string} the product {string} is available")
    public void verifyOverviewPage(String headerOverviewPage, String productName){
        String actualHeaderOverviewPage = overviewObjects.getOverviewPageHeader();
        Assert.assertEquals(actualHeaderOverviewPage, headerOverviewPage, "Header page does not match!");
    
        boolean isProductInCart = cartObjects.verifyProductOnCart(productName);
        Assert.assertTrue(isProductInCart,"Product not found in cart!");

    }

    @And("Buyer click button finish")
    public void buyerGoToCompletePage(){
        overviewObjects.goToFinishCheckout();
    }

    @And("On complete page {string} Buyer will see the message {string} is displayed")
    public void verifyCompletePage(String headerCompletePage, String completeMsg){
        String actualHeaderCompletePage = completeObjects.getCompletePageHeader();
        Assert.assertEquals(actualHeaderCompletePage, headerCompletePage,"Header page does not match!");

        String actualCompleteMsg = completeObjects.getCompleteMsg();
        Assert.assertEquals(actualCompleteMsg, completeMsg, "Complete message page does not match!");

        completeObjects.goToHome();
    }

    @When("User select filter by {string}")
    public void filterProduct(String option){
        dashboardObjects.filterProduct(option);
    }

    @Then("Verify the product filtered by {string}")
    public void verifyFilteredProduct(String option){
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
}
