package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
    features="src/test/resources/features/API.feature", 
    glue={"definitions"},
    plugin = {"pretty",
            "html:target/cucumber-report.html",
            "json:target/cucumber-report.json",
            "junit:target/cucumber-report.xml"},
    monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests{

}
