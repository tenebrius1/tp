package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(defaultConfig.equals(defaultConfig));

        // null -> return false
        assertFalse(defaultConfig.equals(null));

        // different type -> return false
        assertFalse(defaultConfig.equals(5));

        // same values -> return true
        assertTrue(defaultConfig.equals(defaultConfigCopy));

        // different logLevel -> return false
        assertFalse(defaultConfig.equals(configWithDifferentLogLevel));

        // different userPrefsFilePath -> return false
        assertFalse(defaultConfig.equals(configWithDifferentUserPrefsFilePath));
    }
}
