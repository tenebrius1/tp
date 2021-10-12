package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {
    final String validTagName = "PM";
    final String validTagName2 = "SM";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag tag = new Tag(validTagName);
        Tag tagCopy = new Tag(validTagName);
        Tag differentTag = new Tag(validTagName2);

        // same object -> return true
        assertTrue(tag.equals(tag));

        // same tagName -> return true
        assertTrue(tag.equals(tagCopy));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different types -> returns false
        assertFalse(tag.equals(5));

        // different tagName -> returns false
        assertFalse(tag.equals(differentTag));
    }
}
