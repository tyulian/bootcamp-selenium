package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
    features="src/test/resources/features/selenium", 
    glue={"selenium/definitions", "selenium/hook"},
    plugin = {"pretty",
            "html:target/cucumber-report.html",
            "json:target/cucumber-report.json",
            "junit:target/cucumber-report.xml"},
    monochrome = true)
public class SeleniumRunner extends AbstractTestNGCucumberTests{

}
