package configs;

import org.testng.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class AutoConfiguration {
    public static Properties initAutomatioProperties() {
        Properties properties = null;
        try {
            final File file = new File(
                    System.getProperty("user.dir") + File.separator + "Env_configs" + File.separator + "Automation.Properties");
            if (file == null) {
                throw new FileNotFoundException();
            }
            final FileInputStream fileInput = new FileInputStream(file);
            properties = new Properties();
            properties.load(fileInput);
            final String envVal = "AmazonQa";
            if (!Strings.isNullOrEmpty(envVal)) {
                properties.setProperty("Environment", envVal);
            }

            fileInput.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
    public static Properties initEnvironmentProperties() {
        Properties properties = null;
        try {
            final File file = new File(System.getProperty("user.dir") + File.separator + "Environments" + File.separator
                    + initAutomatioProperties().getProperty("Environment") + ".Properties");
            if (file == null) {
                throw new FileNotFoundException();
            }
            final FileInputStream fileInput = new FileInputStream(file);
            properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
