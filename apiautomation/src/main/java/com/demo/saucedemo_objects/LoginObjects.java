package com.demo.saucedemo_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class LoginObjects extends AbstractComponent{
    WebDriver driver;
    AbstractComponent abstractComponent;

    public LoginObjects(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="user-name")
    private WebElement txtUsername;

    @FindBy(id="password")
    private WebElement txtPassword;

    @FindBy(id="login-button")
    private WebElement loginBtn;

    @FindBy(id="react-burger-menu-btn")
    private WebElement menuBtn;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutBtn;

    By errorMsg = By.cssSelector(".h3");
    
        public void loginApplication(String username, String password){
            txtUsername.sendKeys(null == username ? "" : username);
            txtPassword.sendKeys(null == password ? "" : password);
            loginBtn.click();
        }

        public Boolean isErrorMsgVisible(){
            return isElementPresent(errorMsg);
        }

        public String getErrorMsg(){
            return driver.findElement(errorMsg).getText();
        }

        public void logoutApplication(){
            menuBtn.click();
            logoutBtn.click();
        }
}
