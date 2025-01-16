package playwright.skeleton.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import playwright.skeleton.cucumber.TestContext;
import playwright.skeleton.pageobject.AbstractLoginPage;
import org.junit.Assert;

public class LoginStep {

    private final AbstractLoginPage LoginPage;
    private final TestContext testContext;

    public LoginStep(TestContext context) {
        testContext = context;
        this.LoginPage = testContext.getPageObjectManager().getLoginPage();
    }

    @Given("^I log in with (.*) username$")
    public void iLogInWithUsername(String username) throws Exception {
        LoginPage.loginWithUsername(username);
    }

    @Then("I check login is correct")
    public void iCheckLoginIsCorrect() {
        Assert.assertTrue(LoginPage.checkLoginPerform());
    }
}

