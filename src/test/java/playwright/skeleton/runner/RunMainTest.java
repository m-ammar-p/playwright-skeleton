package playwright.skeleton.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to feature files
        glue = "playwright.skeleton.stepdefinition",           // Path to step definitions
        plugin = {
                "pretty",                             // Console output
                "json:target/cucumber-reports/cucumber.json", // JSON report
                "html:target/cucumber-reports/html-report",   // HTML report
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Allure results
        },
        tags = "@Regression"                      // Tags to filter scenarios
)

public class RunMainTest {
}
