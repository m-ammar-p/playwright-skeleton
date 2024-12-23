package playwright.skeleton.pageobject;

import com.microsoft.playwright.Page;

public abstract class AbstractLoginPage extends PageBase {

    public AbstractLoginPage(Page page) {
        super(page);
    }

    private final String userNameId = "#username";
    private final String passwordId = "#password";
    private final String loginBtnId = "#login";
    private final String authErrorClass = ".auth_error";

    public void loginWithUsername(String username) {
        fillElement(username, userNameId);
        fillElement("3123", passwordId);
        clickOnClickableElement(loginBtnId);
    }

    public boolean checkLoginPerform() {
        waitUntilElementIsVisible(authErrorClass);
        return elementIsVisible(authErrorClass);
    }
}
