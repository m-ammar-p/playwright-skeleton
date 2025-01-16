package playwright.skeleton.pageobject.helpers;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.util.List;

public interface Findable extends PageContainer {
    // Default method to find a single element using a string selector
    default ElementHandle findElement(String selector) {
        Page page = getPage();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
        }
        return page.querySelector(selector); // Use querySelector for a single element
    }

    // Default method to find multiple elements using a string selector
    default List<ElementHandle> findElements(String selector) {
        Page page = getPage();
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Ensure getPage() returns a valid Page object.");
        }
        return page.querySelectorAll(selector); // Use querySelectorAll for multiple elements
    }

    // Default method to find an element by index from a list of elements using a string selector
    default ElementHandle findElementFromAListByIndex(String selector, int index) {
        List<ElementHandle> elements = findElements(selector);
        if (elements.size() <= index) {
            throw new IndexOutOfBoundsException("No element found at index " + index);
        }
        return elements.get(index);
    }
}
