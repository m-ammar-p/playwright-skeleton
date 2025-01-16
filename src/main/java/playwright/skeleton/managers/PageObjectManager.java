package playwright.skeleton.managers;

import com.microsoft.playwright.Page;
import playwright.skeleton.pageobject.loginpage.LoginPage;

public class PageObjectManager {

    private final Page page;
    private LoginPage LoginPage;

    public PageObjectManager(Page page) {
        this.page = page;
    }

    public LoginPage getLoginPage() {
        if (LoginPage == null) {
            LoginPage = new LoginPage(page);
        }
        return LoginPage;
    }
}
