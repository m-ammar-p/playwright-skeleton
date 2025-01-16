package playwright.skeleton.pageobject;

import com.microsoft.playwright.Page;
import playwright.skeleton.pageobject.helpers.*;


public class PageBase implements PageContainer, Fillable, Waitable, Findable, Conditionable, Clickable {

    protected Page page;

    protected PageBase(Page page) {
        this.page = page;
    }

    @Override
    public Page getPage() {
        return page; // Ensure the page is not null
    }
}
