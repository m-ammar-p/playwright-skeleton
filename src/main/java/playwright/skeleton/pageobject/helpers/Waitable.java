package playwright.skeleton.pageobject.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public interface Waitable extends PageContainer {

    Page getPage();

    default void waitUntilElementIsVisible(String selector) {
        Page page = getPage();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
        }
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }
}
