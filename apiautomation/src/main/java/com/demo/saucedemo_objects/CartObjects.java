package com.demo.saucedemo_objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.demo.abstractcomponents.AbstractComponent;

public class CartObjects extends AbstractComponent{
WebDriver driver;

    public CartObjects( WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    
    @FindBy(id="checkout")
    private WebElement checkoutBtn;

    @FindBy(css=".cart_item button")
    private WebElement removeBtn;

    By listProd = By.className("cart_item");
    By titleProduct = By.className("inventory_item_name");
    By deleteBtn = By.cssSelector(".cart_item button");
    
    public void goToCheckoutProduct(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);        
        checkoutBtn.click();
    }

    public Boolean verifyProductOnCart(String productName){
        visibilityOfElementLocated(listProd);
        List<WebElement> cartProducts = driver.findElements(titleProduct);        
        return cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));
    }

    public WebElement getProductOnCart(String productName){
        List<WebElement> products = driver.findElements(listProd);
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(titleProduct).getText().equals(productName)).findFirst().orElse(null);    
        return productToSelect;
    }

    public void removeProduct(String productName){
        visibilityOfElementLocated(listProd);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", removeBtn);
        getProductOnCart(productName).findElement(deleteBtn).click();    
    }

    public Boolean verifyDeletedItem(String productName) {
        By deletedProduct = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        return invisibilityOfElementLocated(deletedProduct);
    }

}
