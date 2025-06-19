package playwright.skeleton.stepdefinition;

import com.microsoft.playwright.*;
import io.cucumber.java.*;
import playwright.skeleton.cucumber.TestContext;
import playwright.skeleton.dataproviders.ConfigFileReader;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

public class Hooks {

    private static final Logger LOGGER = Logger.getLogger(Hooks.class.getName());

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUpTest() {
        playwright = Playwright.create();

        boolean isCI = System.getenv("CI") != null;

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isCI)
                .setArgs(Arrays.asList(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--enable-automation"
                ));

        browser = playwright.chromium().launch(options);
        context = browser.newContext();
        page = context.newPage();
        testContext.setPage(page);

        if (!isCI) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            int buffer = 20;
            page.setViewportSize(screenWidth - buffer, screenHeight - buffer);
        }

        // Now using config file for base URL
        String baseUrl = ConfigFileReader.getApplicationUrl();
        LOGGER.info("Navigating to base URL: " + baseUrl);
        page.navigate(baseUrl);
        LOGGER.info("Test setup complete.");
    }

    @After
    public void tearDownTest(Scenario scenario) {
        try {
            if (page != null && scenario.isFailed()) {
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("target/screenshots/" + scenario.getName() + ".png")));
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            }
        } catch (Exception e) {
            LOGGER.severe("Failed to take screenshot: " + e.getMessage());
        } finally {
            if (context != null) context.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
        LOGGER.info("Test teardown complete.");
    }
}