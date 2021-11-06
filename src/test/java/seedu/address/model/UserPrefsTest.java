package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public void setCliTutorsFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setCliTutorsFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs userPrefsCopy = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(600, 750, 0 , 0));

        // same values -> returns true
        assertEquals(userPrefs, userPrefsCopy);

        // null -> returns false
        assertNotEquals(null, userPrefs);

        // same object -> returns true
        assertEquals(userPrefs, userPrefs);

        // different type -> returns false
        assertNotEquals(5, userPrefs);

        // different GUISetting -> returns false
        assertNotEquals(userPrefs, differentUserPrefs);
    }
}
