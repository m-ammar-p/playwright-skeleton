package playwright.skeleton.runner;

import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber") // Use Cucumber engine
@SelectClasspathResource("features")// Specify the folder containing feature files
@CucumberOptions(
        plugin = {
                "pretty",
                "json:target/cucumber-report/cucumber.json"  // Output to this directory
        }
)
public class RunMainTest {
}
