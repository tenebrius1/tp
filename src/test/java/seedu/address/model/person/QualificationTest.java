package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QualificationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Qualification(null));
    }

    @Test
    public void constructor_invalidQualification_throwsIllegalArgumentException() {
        String invalidQualification = "";
        assertThrows(IllegalArgumentException.class, () -> new Qualification(invalidQualification));
    }

    @Test
    public void isValidQualification() {
        // null name
        assertThrows(NullPointerException.class, () -> Qualification.isValidQualification(null));

        // invalid qualification
        assertFalse(Qualification.isValidQualification("")); // empty string
        assertFalse(Qualification.isValidQualification(" ")); // spaces only
        assertFalse(Qualification.isValidQualification("^")); // only non-alphanumeric characters
        assertFalse(Qualification.isValidQualification("4")); // out of the valid range

        // valid qualification
        assertTrue(Qualification.isValidQualification("1")); // within valid range
    }
}
