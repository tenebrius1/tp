package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "This is a long remark that exceeds 100 characters. "
                + "This should not be allowed as a remark and should throw an error.";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remark
        assertFalse(Remark.isValidRemark("This is a long remark that exceeds 100 characters. "
                + "This should not be allowed as a remark and should throw an error.")); // more than 100 characters

        // valid remark
        assertTrue(Remark.isValidRemark("prefers small groups")); // alphabets only
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("lives on 2nd street")); // alphanumeric characters
        assertTrue(Remark.isValidRemark("Capital Tan")); // with capital letters
        assertTrue(Remark.isValidRemark("!@#$%")); // symbols
    }

    @Test
    public void equals() {
        // same values -> returns true
        Remark remark = BENSON.getRemark();
        Remark remarkCopy = new Remark("Wants student in Ang Mo Kio");
        assertTrue(remark.equals(remarkCopy));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different type -> returns false
        assertFalse(remark.equals(5));

        // different remark -> returns false
        Remark remarkDifferent = new Remark("F");
        assertFalse(remark.equals(remarkDifferent));
    }
}
