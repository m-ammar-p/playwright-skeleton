package playwright.skeleton.dataproviders;

import playwright.skeleton.exception.CompanyException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class ConfigFileReader {

    private static final Properties properties = new Properties();
    private static final String ENVIRONMENT = "environment";

    static {
        // Fetch the environment from system properties (default to "dev" if not set)
        String environment = System.getProperty("env", "dev");
        System.out.println("Active Environment: " + environment);

        if (environment == null || environment.isEmpty()) {
            throw new CompanyException("Environment not specified. Please use the correct Maven profile.");
        }

        // Set the path for the properties file based on the environment (test or ua)
        String propertiesFilePath = Paths.get(System.getProperty("user.dir"), "profiles", environment, "company.properties").toString();

        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFilePath))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new CompanyException("Error loading properties file: " + propertiesFilePath, e);
        }
    }

    private ConfigFileReader() {}

    public static String getApplicationUrl() {
        String url = properties.getProperty("urlCompany");
        if (url != null) return url;
        else
            throw new CompanyException("urlCompany not specified in the properties file.");
    }

    public static String getEnvironment() {
        String environment = properties.getProperty(ENVIRONMENT);
        if (environment != null) return environment;
        else
            throw new CompanyException("environment not specified in the properties file.");
    }

    public static Duration getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null) return Duration.ofSeconds(Long.parseLong(implicitlyWait));
        else
            throw new CompanyException("implicitlyWait not specified in the properties file.");
    }

    public static Long getWaitSeconds() {
        String waitSeconds = properties.getProperty("waitSeconds");
        if (waitSeconds != null) return Long.parseLong(waitSeconds);
        else
            throw new CompanyException("waitSeconds not specified in the properties file.");
    }

}
