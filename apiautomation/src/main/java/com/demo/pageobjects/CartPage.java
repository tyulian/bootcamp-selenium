package com.demo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class CartPage extends AbstractComponent{
    WebDriver driver;

    public CartPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
@FindBy(css=".totalRow button")
private WebElement checkoutBtn;

@FindBy(css=".cartSection h3")
private List<WebElement> listOfProduct;

By rowBtn = By.cssSelector(".totalRow button");
By listProd = By.cssSelector(".cartSection h3");

public void goToCheckoutProduct(){
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);        
    visibilityOfElementLocated(rowBtn);
    elementToBeClickable(rowBtn);
    checkoutBtn.click();
}

public Boolean verifyCheckoutProduct(String productName){
    visibilityOfElementLocated(listProd);
    return listOfProduct.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));

}

}
