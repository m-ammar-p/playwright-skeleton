package playwright.skeleton.pageobject.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public interface Clickable extends PageContainer {

    // Provide the Page object that subclasses will use
    Page getPage();

    // Default method to click on an element using a selector
    default void clickOnClickableElement(String selector) {
        Page page = getPage();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
        }

        page.locator(selector).click();
    }

    // Default method to click on an element using a Locator
    default void clickOnClickableElement(Locator element) {
        if (element == null) {
            throw new IllegalArgumentException("Element locator is null. Ensure a valid Locator is provided.");
        }
        element.click();
    }
}
