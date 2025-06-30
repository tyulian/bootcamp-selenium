package com.demo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class OrderPage extends AbstractComponent{
    WebDriver driver;

    public OrderPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="[placeholder='Select Country']")
    private WebElement selectCountry;

    @FindBy(xpath="//span[@class='ng-star-inserted']")
    private List<WebElement> listCountry;

    @FindBy(css=".action__submit")
    private WebElement submitBtn;

    By countrySuggestion = By.cssSelector(".ta-results");

    public void selectCountry(String destination){
        Actions action = new Actions(driver);
        action.sendKeys(selectCountry,"ind").build().perform();
        visibilityOfElementLocated(countrySuggestion);
        elementToBeClickable(countrySuggestion);
        WebElement countryToSelect = listCountry.stream().filter(country -> country.getText().equalsIgnoreCase(destination)).findFirst().orElse(null);

        countryToSelect.click();
        
    }

    public void submitOrder(){
        submitBtn.click();
    }
}
