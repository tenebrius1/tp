package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Qualification.Qualifications;

class QualificationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Qualification(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidQualification = "";
        assertThrows(IllegalArgumentException.class, () -> new Qualification(invalidQualification));
    }

    @Test
    void isValidQualification() {
        // null qualification
        assertThrows(NullPointerException.class, () -> Qualification.isValidQualification(null));

        // invalid qualification
        assertFalse(Qualification.isValidQualification("")); // empty string
        assertFalse(Qualification.isValidQualification(" ")); // spaces only
        assertFalse(Qualification.isValidQualification("invalidthing")); // non-numeric
        assertFalse(Qualification.isValidQualification("69")); // not a valid qualification code

        // valid qualification
        assertTrue(Qualification.isValidQualification("3"));
    }

    @Test
    public void testGetLabel() {
        assertEquals("MOE-Trained", Qualifications.getLabel("3"));
        assertEquals("Invalid", Qualifications.getLabel("FailTest"));
    }
}
