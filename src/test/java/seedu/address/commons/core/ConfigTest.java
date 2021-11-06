package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {
    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void getUserPrefsFilePath_defaultObject_pathReturned() {
        Path defaultPath = Paths.get("preferences.json");
        assertEquals(defaultPath, new Config().getUserPrefsFilePath());
    }

    @Test
    public void getLogLevel_defaultObject_levelReturned() {
        assertEquals(Level.INFO, new Config().getLogLevel());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        Config defaultConfigCopy = new Config();

        Config configWithDifferentLogLevel = new Config();
        configWithDifferentLogLevel.setLogLevel(Level.ALL);
        Config configWithDifferentUserPrefsFilePath = new Config();
        configWithDifferentUserPrefsFilePath.setUserPrefsFilePath(Paths.get("test.json"));

        assertNotNull(defaultConfig);

        // same object -> return true
        assertEquals(defaultConfig, defaultConfig);

        // null -> return false
        assertNotEquals(null, defaultConfig);

        // different type -> return false
        assertNotEquals(5, defaultConfig);

        // same values -> return true
        assertEquals(defaultConfig, defaultConfigCopy);

        // different logLevel -> return false
        assertNotEquals(defaultConfig, configWithDifferentLogLevel);

        // different userPrefsFilePath -> return false
        assertNotEquals(defaultConfig, configWithDifferentUserPrefsFilePath);
    }
}
