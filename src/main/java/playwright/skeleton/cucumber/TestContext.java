package playwright.skeleton.cucumber;

import com.microsoft.playwright.Page;
import playwright.skeleton.managers.PageObjectManager;

public class TestContext {

    private PageObjectManager pageObjectManager;
    private final ScenarioContext scenarioContext;
    private Page page;

    public TestContext() {
        scenarioContext = new ScenarioContext();
    }

    public void setPage(Page page) {
        this.page = page;
        this.pageObjectManager = new PageObjectManager(page);
    }

    public PageObjectManager getPageObjectManager() {
        if (pageObjectManager == null) {
            throw new IllegalStateException("PageObjectManager is not initialized. Call setPage(Page) first.");
        }
        return pageObjectManager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public Page getPage() {
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Call setPage(Page) first.");
        }
        return page;
    }
}
