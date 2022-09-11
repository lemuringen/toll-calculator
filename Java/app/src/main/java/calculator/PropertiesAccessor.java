package calculator;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesAccessor
{
    private PropertiesAccessor()
    {
    }


    private static final Properties secretProperties = null;
    private static final Properties calendarRegionsProperties = null;
    private static final Properties settingsProperties = null;
    private static final String SECRET_PROPERTIES_FILE_NAME = "secrets.properties";
    private static final String CALENDAR_REGIONS_PROPERTIES_NAME = "calendar-regions.properties";
    private static final String SETTINGS_PROPERTIES_FILE_NAME = "app-settings.properties";

    private static Properties loadProperties(String propertiesFileName)
    {
        Properties prop = null;
        try (InputStream input = PropertiesAccessor.class.getClassLoader()
                                                         .getResourceAsStream(propertiesFileName))
        {
            if (input != null)
            {
                prop = new Properties();
                prop.load(input);
            }
        } catch (IOException ex)
        {
            log.error(ex.getMessage());
        }
        return prop;
    }

    public static String getSecretProperty(String name, String defaultValue)
    {
        return getProperty(name,
                           defaultValue,
                           secretProperties,
                           SECRET_PROPERTIES_FILE_NAME,
                           "Unable to load: " + SECRET_PROPERTIES_FILE_NAME);
    }
    public static String getSettingsProperty(String name, String defaultValue)
    {
        return getProperty(name,
                           defaultValue,
                           settingsProperties,
                           SETTINGS_PROPERTIES_FILE_NAME,
                           "Unable to load: " + SETTINGS_PROPERTIES_FILE_NAME);
    }
    public static String getCalendarRegionsProperty(String name, String defaultValue)
    {
        return getProperty(name,
                           defaultValue,
                           calendarRegionsProperties,
                           CALENDAR_REGIONS_PROPERTIES_NAME,
                           "Unable to load: " + CALENDAR_REGIONS_PROPERTIES_NAME);
    }
    public static String getProperty(String name,
                                     String defaultValue,
                                     Properties properties,
                                     String fileName,
                                     String msgOnError)
    {
        if (properties == null)
        {
            properties = loadProperties(fileName);
            if (properties == null) log.error(msgOnError);
        }
        return properties == null ? defaultValue : properties.getProperty(name, defaultValue);
    }
}