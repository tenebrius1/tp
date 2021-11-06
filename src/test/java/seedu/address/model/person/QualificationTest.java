package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.CARL;

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

    @Test
    public void equals() {
        // same values -> returns true
        Qualification qualification = CARL.getQualification();
        Qualification qualificationCopy = new Qualification("2");
        assertEquals(qualification, qualificationCopy);

        // same object -> returns true
        assertEquals(qualification, qualification);

        // null -> returns false
        assertNotEquals(null, qualification);

        // different type -> returns false
        assertNotEquals(5, qualification);

        // different qualification -> returns false
        Qualification qualificationDifferent = new Qualification("3");
        assertNotEquals(qualification, qualificationDifferent);
    }
}
