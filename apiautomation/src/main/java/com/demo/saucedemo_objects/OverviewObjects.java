package com.demo.saucedemo_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class OverviewObjects extends AbstractComponent {
    WebDriver driver;

    public OverviewObjects(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//span[@class = 'title']")
    private WebElement headerOverviewPage;

    @FindBy(id=("finish"))
    private WebElement finishBtn;

    By titleOverviewPage = By.xpath("//span[@class = 'title']");

    public String getOverviewPageHeader(){
        visibilityOfElementLocated(titleOverviewPage);
        return headerOverviewPage.getText();       
    }

    public void goToFinishCheckout(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finishBtn);
        finishBtn.click();
    }

}
