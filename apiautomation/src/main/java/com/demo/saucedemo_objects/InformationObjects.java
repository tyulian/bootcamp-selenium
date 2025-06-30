package com.demo.saucedemo_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class InformationObjects extends AbstractComponent{
    WebDriver driver;
    
    public InformationObjects(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//span[@class = 'title']")
    private WebElement headerInfoPage;

    @FindBy(id=("first-name"))
    private WebElement txtFirstName;

    @FindBy(id=("last-name"))
    private WebElement txtLastName;

    @FindBy(id=("postal-code"))
    private WebElement txtPostalCode;

    @FindBy(id=("continue"))
    private WebElement continueBtn;

    By titleInfoPage = By.xpath("//span[@class = 'title']");

    public String getInformationPageHeader(){
        visibilityOfElementLocated(titleInfoPage);
        return headerInfoPage.getText();       
    }

    public void addInformation(String firstName, String lastName, String postalCode){
        txtFirstName.sendKeys(null == firstName ? "" : firstName);
        txtLastName.sendKeys(null == lastName ? "" : lastName);
        txtPostalCode.sendKeys(null == postalCode ? "" : postalCode);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        continueBtn.click();
    }
}
