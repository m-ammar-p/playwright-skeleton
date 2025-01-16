package playwright.skeleton.pageobject.helpers;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

public interface Conditionable extends Waitable {

    // Default method to check if an element is visible using a string selector
    default boolean elementIsVisible(String selector) {
        Page page = getPage();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
        }

        // Use the Page object to check visibility using querySelector
        ElementHandle element = page.querySelector(selector);

        // Check if the element exists and is visible
        return element != null && element.isVisible();
    }
}

