package com.demo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class LoginPage extends AbstractComponent{
    WebDriver driver;
    AbstractComponent abstractComponent;

    public LoginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="userEmail")
    private WebElement userEmail;

    @FindBy(id="userPassword")
    private WebElement userPassword;

    @FindBy(id="login")
    private WebElement loginBtn;

    By emailErrorMsg = By.xpath("//input[@id='userEmail']/following-sibling::div//div[@class='ng-star-inserted']");
    By passwordErrorMsg = By.xpath("//input[@id='userPassword']/following-sibling::div//div[@class='ng-star-inserted']");


    /*
        //Insert valid credentials
        driver.findElement(By.id("userEmail")).sendKeys("testqa.wrc@gmail.com");
        - Find Element by ID -> Page Factory
        - Input email -> method sendKeys
        
        driver.findElement(By.id("userPassword")).sendKeys("@Dmin123");
        - Find Element by ID -> Page Factory
        - Input email -> method sendKeys

        driver.findElement(By.id("login")).click();
         */

        public void loginApplication(String email, String password){
            userEmail.sendKeys(null == email ? "" : email);
            userPassword.sendKeys(null == password ? "" : password);
            loginBtn.click();
        }

        public Boolean isEmailErrorMsgVisible(){
            return isElementPresent(emailErrorMsg);
        }

        public Boolean isPasswordErrorMsgVisible(){
            return isElementPresent(passwordErrorMsg);
         }

        public String getEmailErrorMsg(){
            return driver.findElement(emailErrorMsg).getText();
        }

         public String getPasswordErrorMsg(){
            return driver.findElement(passwordErrorMsg).getText();
        }
}
