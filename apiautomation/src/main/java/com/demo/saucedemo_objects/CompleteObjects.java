package com.demo.saucedemo_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class CompleteObjects extends AbstractComponent {
    WebDriver driver;

    public CompleteObjects(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//span[@class = 'title']")
    private WebElement headerCompletePage;

    @FindBy(className="complete-header")
    private WebElement completeMsg;

    @FindBy(id=("back-to-products"))
    private WebElement backHomeBtn;

    By titleCompletePage = By.xpath("//span[@class = 'title']");

    public String getCompletePageHeader(){
        visibilityOfElementLocated(titleCompletePage);
        return headerCompletePage.getText();       
    }

    public String getCompleteMsg(){
        return completeMsg.getText();  
    }

    public void goToHome(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", backHomeBtn);
        backHomeBtn.click();
    }

}
