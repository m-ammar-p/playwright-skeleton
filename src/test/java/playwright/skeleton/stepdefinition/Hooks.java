package playwright.skeleton.stepdefinition;

import com.microsoft.playwright.*;
import io.cucumber.java.*;
import playwright.skeleton.cucumber.TestContext;
import playwright.skeleton.dataproviders.ConfigFileReader;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.Arrays;

public class Hooks {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @BeforeAll
    public static void beforeAll() {

        String applicationUrl = ConfigFileReader.getApplicationUrl();
        String implicitlyWait = ConfigFileReader.getImplicitlyWait().toString();
        String waitSeconds = ConfigFileReader.getWaitSeconds().toString();

    }

    @Before
    public void setUpTest() {
        // Initialize Playwright and browser
        playwright = Playwright.create();

        // Configure browser options
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false)  // To make it run in non-headless mode (visible)
                .setArgs(Arrays.asList(
                        "--no-sandbox",           // Disable sandbox for security reasons
                        "--disable-dev-shm-usage", // Disable /dev/shm usage (helpful for Docker environments)
                        "--disable-gpu",           // Disable GPU (if running in headless mode)
                        "--enable-automation"      // Enable automation in Playwright
                ));

        // Launch the browser with the specified options
        browser = playwright.chromium().launch(options);

        // Create a new browser context and page
        context = browser.newContext();
        page = context.newPage();

        // Set the page in TestContext
        testContext.setPage(page);

        // Set the window size to a large resolution or dynamic screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int buffer = 20; // Adjust this value based on your screen
        page.setViewportSize(screenWidth - buffer, screenHeight - buffer);

        // Fetch the base URL from the ConfigFileReader based on the environment
        String baseUrl = ConfigFileReader.getApplicationUrl(); // This will fetch URL based on the environment (test or ua)
        page.navigate(baseUrl);

        System.out.println("Test setup complete.");
    }

    @After
    public void tearDownTest(Scenario scenario) {
        // Take a screenshot if the scenario fails
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("target/screenshots/" + scenario.getName() + ".png")));
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // Attach to Allure with correct MIME type and extension
            io.qameta.allure.Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), "png");
        }

        // Close the browser context and Playwright
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }

        System.out.println("Test teardown complete.");
    }

    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("target/screenshots/" + scenario.getName() + "_step.png")));
            scenario.attach(screenshot, "image/png", scenario.getName() + "_step");
            io.qameta.allure.Allure.addAttachment(scenario.getName() + "_step", "image/png", new ByteArrayInputStream(screenshot), "png");
        }
    }
}