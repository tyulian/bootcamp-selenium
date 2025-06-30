package com.demo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demo.abstractcomponents.AbstractComponent;

public class DashboardPage extends AbstractComponent {
    WebDriver driver;

    public DashboardPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /*public void implementFilter(){}
    public void inputRangePrice(String minPrice, String maxPrice){}*/

    @FindBy(xpath = "//div[@class = 'left mt-1']/p")
    private WebElement homePageText;

    @FindBy(css="[routerlink*='/dashboard/cart']")
    private WebElement cartBtn;

    By listOfProducts = By.cssSelector(".mb-3");
    By titleProduct = By.cssSelector(".card-body b");
    By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
    By loadingWait = By.xpath("//ngx-spinner[@class = 'ng-tns-c31-3 ng-star-inserted']");

    public String getHomePageText(){
        return homePageText.getText();
    }

    public WebElement getProductByName(String productName){
        List<WebElement> products = driver.findElements(listOfProducts);
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(titleProduct).getText().equals(productName)).findFirst().orElse(null);    
        return productToSelect;
    }

    public void addToCart(String productName){
        visibilityOfElementLocated(listOfProducts);
        getProductByName(productName).findElement(addToCartBtn).click();
    
    }

    public void clickOnCart(){
        invisibilityOfElementLocated(loadingWait);
        cartBtn.click();
    }
}
