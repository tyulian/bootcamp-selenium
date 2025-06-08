package firstcourse;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParallelTestNG {
    String name = "AfterOffice1";

   
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

   
}
