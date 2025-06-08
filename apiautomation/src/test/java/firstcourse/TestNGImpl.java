package firstcourse;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGImpl {
    String name = "AfterOffice1";

    @BeforeClass
    public void setUpClass(){
        System.out.println("Ini untuk setup before class");
    }

    @BeforeMethod
    public void setUp(){
        System.out.println("Before Method");
    }

    @Test
    public void scenarioTest1(){
        Assert.assertEquals(name, "AfterOffice1");
        System.out.println("Scenario 1");
    }

    @Test
    public void scenarioTest2(){
        Assert.assertEquals(name, "AfterOffice1");
        System.out.println("Scenario 2");
    }

    @Test
    public void scenarioTest3(){
        Assert.assertEquals(name, "AfterOffice1");
        System.out.println("Scenario 3");
    }

    @AfterMethod
    public void scenarioTest4(){
        System.out.println("After Method");
    }

    @AfterClass
    public void setUpAfterClass(){
        System.out.println("Ini adalah setup after class");
    }
}
