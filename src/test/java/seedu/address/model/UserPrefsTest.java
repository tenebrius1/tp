package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {
    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs userPrefsCopy = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(600, 750, 0 , 0));

        // same values -> returns true
        assertTrue(userPrefs.equals(userPrefsCopy));

        // null -> returns false
        assertFalse(userPrefs.equals(null));

        // same object -> returns true
        assertTrue(userPrefs.equals(userPrefs));

        // different type -> returns false
        assertFalse(userPrefs.equals(5));

        // different GUISetting -> returns false
        assertFalse(userPrefs.equals(differentUserPrefs));
    }
}
