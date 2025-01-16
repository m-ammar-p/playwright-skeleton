package playwright.skeleton.pageobject.helpers;

import com.microsoft.playwright.Page;


public interface Fillable extends Waitable {

    // Provide the Page object that subclasses will use
    Page getPage();

    // Default method for filling an element
    default void fillElement(String value, String selector) {
        if (value != null && !value.isEmpty()) {
            Page page = getPage();
            if (page == null) {
                throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
            }
            // Clear and then fill the input field
            page.fill(selector, value);
        }
    }
}

