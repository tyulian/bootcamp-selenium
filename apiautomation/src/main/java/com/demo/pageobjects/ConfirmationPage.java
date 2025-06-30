package com.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent{
    WebDriver driver;
    
    public ConfirmationPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css=".hero-primary") 
    private WebElement confirmationMsg;

    By confirmText = By.cssSelector(".hero-primary");

    public String getConfirmationMsg(){
        visibilityOfElementLocated(confirmText);
        return confirmationMsg.getText();
    }

}
