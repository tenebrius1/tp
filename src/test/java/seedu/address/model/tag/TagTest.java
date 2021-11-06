package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertEquals(tag, tag);

        // same tagName -> return true
        assertEquals(tag, tagCopy);

        // null -> returns false
        assertNotEquals(null, tag);

        // different types -> returns false
        assertNotEquals(5, tag);

        // different tagName -> returns false
        assertNotEquals(tag, differentTag);
    }
}
