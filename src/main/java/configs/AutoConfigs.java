package configs;

public class AutoConfigs {
    public static String amazonEnv = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrl");
    public static String apiUrl = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrl");
}
