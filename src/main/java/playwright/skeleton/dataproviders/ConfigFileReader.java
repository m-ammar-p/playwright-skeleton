package playwright.skeleton.dataproviders;

import playwright.skeleton.exception.CompanyException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigFileReader {

    private static final Logger LOGGER = Logger.getLogger(ConfigFileReader.class.getName());
    private static final Properties properties = new Properties();

    static {
        String environment = System.getProperty("env", "dev");
        LOGGER.info("Active Environment: " + environment);

        String propertiesFilePath = Paths.get(
                System.getProperty("user.dir"),
                "profiles", "env", environment, "company.properties"
        ).toString();

        try (BufferedReader reader = new BufferedReader(new FileReader(propertiesFilePath))) {
            properties.load(reader);
            LOGGER.info("Loaded config from: " + propertiesFilePath);
        } catch (IOException e) {
            throw new CompanyException("Error loading properties file: " + propertiesFilePath, e);
        }
    }

    private ConfigFileReader() {
    }

    public static String getApplicationUrl() {
        String url = properties.getProperty("urlCompany");
        if (url != null) return url;
        throw new CompanyException("urlCompany not specified in the properties file.");
    }

    public static String getEnvironment() {
        String environment = properties.getProperty("environment");
        if (environment != null) return environment;
        throw new CompanyException("environment not specified in the properties file.");
    }

    public static Long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null) {
            return Long.parseLong(implicitlyWait) * 1000; // seconds to ms
        }
        throw new CompanyException("implicitlyWait not specified in the properties file.");
    }

    public static Long getWaitSeconds() {
        String waitSeconds = properties.getProperty("waitSeconds");
        if (waitSeconds != null) {
            return Long.parseLong(waitSeconds) * 1000; // seconds to ms
        }
        throw new CompanyException("waitSeconds not specified in the properties file.");
    }
}
