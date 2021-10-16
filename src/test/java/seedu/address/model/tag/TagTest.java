package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {
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
        Tag tag = new Tag(VALID_TAG_PM);
        Tag tagCopy = new Tag(VALID_TAG_PM);
        Tag differentTag = new Tag(VALID_TAG_TP);

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
