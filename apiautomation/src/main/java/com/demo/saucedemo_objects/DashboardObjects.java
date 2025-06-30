package com.demo.saucedemo_objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.demo.abstractcomponents.AbstractComponent;

public class DashboardObjects extends AbstractComponent{
    WebDriver driver;

    public DashboardObjects( WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//span[@class = 'title']")
    private WebElement homePageText;

    @FindBy(css=".pricebar button")
    private WebElement addBtn;

    @FindBy(className="shopping_cart_link")
    private WebElement cartBtn;

    @FindBy(className="product_sort_container")
    private WebElement dpFilterProduct;

    By listOfProducts = By.className("inventory_item");
    By titleProduct = By.className("inventory_item_name");
    By addToCartBtn = By.cssSelector(".pricebar button");
    By priceElements = By.className("inventory_item_price");

    public String getHomePageText(){
        return homePageText.getText();
    }   

    public WebElement getProductByName(String productName){
        List<WebElement> products = driver.findElements(listOfProducts);
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(titleProduct).getText().equals(productName)).findFirst().orElse(null);    
        return productToSelect;
    }

    public void addToCart(String productName){
        visibilityOfElementLocated(listOfProducts);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
        getProductByName(productName).findElement(addToCartBtn).click();    
    }

    public void clickOnCart(){
        cartBtn.click();
    }

    public void filterProduct(String option){
        dpFilterProduct.click();
        Select optionFilter = new Select(dpFilterProduct);
        optionFilter.selectByVisibleText(option);
    }

    public List<Double> getProductPrices(){
        visibilityOfElementLocated(listOfProducts);
        List <WebElement> productPrices = driver.findElements(priceElements);
        return productPrices.stream()
        .map(price -> Double.parseDouble(price.getText().replace("$", "")))
        .collect(Collectors.toList());
    }

    public  List<Double> sortPricesLowToHigh(List<Double> prices){
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return sortedPrices;
    }

    public List<Double> sortPricesHighToLow(List<Double> prices) {
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Collections.reverseOrder());
        return sorted;
    }

    public List<String> getProductNameList(){
        List<WebElement> productElements = driver.findElements(titleProduct);
        return productElements.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
    }

    public List<String> sortProductNamesAsc(List<String> productNames) {
        List<String> sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames); // Sort ascending (A–Z)
        return sortedNames;
    }

    public List<String> sortProductNamesDesc(List<String> productNames) {
        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort(Collections.reverseOrder()); // Sort descending (Z–A)
        return sortedNames;
    }


}
