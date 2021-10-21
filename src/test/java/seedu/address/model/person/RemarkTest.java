package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = " ";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remark
        assertFalse(Remark.isValidRemark(" ")); // one space only
        assertFalse(Remark.isValidRemark("   ")); // multiple spaces only

        // valid remark
        assertTrue(Remark.isValidRemark("prefers small groups")); // alphabets only
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("lives on 2nd street")); // alphanumeric characters
        assertTrue(Remark.isValidRemark("Capital Tan")); // with capital letters
        assertTrue(Remark.isValidRemark("!@#$%")); // symbols
    }
}
