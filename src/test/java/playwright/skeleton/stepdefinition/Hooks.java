package playwright.skeleton.stepdefinition;

import com.microsoft.playwright.*;
import io.cucumber.java.*;
import playwright.skeleton.cucumber.TestContext;


import java.awt.*;
import java.io.ByteArrayInputStream;
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

//    @BeforeAll
//    public static void beforeAll() {
//
//        String applicationUrl = ConfigFileReader.getApplicationUrl();
//        String implicitlyWait = ConfigFileReader.getImplicitlyWait().toString();
//        String waitSeconds = ConfigFileReader.getWaitSeconds().toString();
//
//    }

    @Before
    public void setUpTest() {
        // Initialize Playwright and browser
        playwright = Playwright.create();

        // Detect if the environment is CI
        boolean isCI = System.getenv("CI") != null;

        // Configure browser options
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(isCI)  // Use headless mode in CI
                .setArgs(Arrays.asList(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--enable-automation"
                ));

        // Launch the browser with the specified options
        browser = playwright.chromium().launch(options);

        // Create a new browser context and page
        context = browser.newContext();
        page = context.newPage();

        // Set the page in TestContext
        testContext.setPage(page);

        // Set window size for local tests
        if (!isCI) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();

            int buffer = 20; // Adjust this value based on your screen
            page.setViewportSize(screenWidth - buffer, screenHeight - buffer);
        }

        // Navigate to the base URL
//        String baseUrl = ConfigFileReader.getApplicationUrl();
//        page.navigate(baseUrl);

        // Retrieve the environment property with "dev" as the default
        String env = System.getProperty("env", "dev");

// Determine the base URL based on the environment
        String baseUrl = "ua".equals(env)
                ? "https://adactinhotelapp.com/HotelAppBuild2/"
                : "https://adactinhotelapp.com/";

// Log the selected environment and URL for debugging
        LOGGER.info(String.format("Environment: %s%nUsing base URL: %s", env, baseUrl));

// Navigate to the base URL
        page.navigate(baseUrl);
        LOGGER.info("Test setup complete.");
    }

    @After
    public void tearDownTest(Scenario scenario) {
        try {
            if (page != null) {
                if (scenario.isFailed()) {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get("target/screenshots/" + scenario.getName() + ".png")));
                    scenario.attach(screenshot, "image/png", "Failure Screenshot");
                }
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

    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("target/screenshots/" + scenario.getName() + "_step.png")));
            scenario.attach(screenshot, "image/png", scenario.getName() + "_step");
            io.qameta.allure.Allure.addAttachment(scenario.getName() + "_step", "image/png", new ByteArrayInputStream(screenshot), "png");
        }
    }
}