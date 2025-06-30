package com.demo.abstractcomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {
    WebDriver driver;

    public AbstractComponent(WebDriver driver){
        this.driver = driver;
    }
    public boolean isElementPresent(By locator){
        try{
            driver.findElement(locator);
            return true;        
        } catch (Exception e){
            return false;
        }

    }

    public void visibilityOfElementLocated(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean invisibilityOfElementLocated(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void elementToBeClickable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
