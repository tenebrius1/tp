package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;

import org.junit.jupiter.api.Test;

class LevelSubjectCodeTest {

    @Test
    public void testIsValidTag() {
        assertEquals(true, LevelSubjectCode.isValidTag(VALID_TAG_PM));
        assertEquals(false, LevelSubjectCode.isValidTag("ABCDE"));
    }

    @Test
    public void testGetLabel() {
        assertEquals("PriMath", LevelSubjectCode.getLabel(VALID_TAG_PM));
        assertEquals("Invalid", LevelSubjectCode.getLabel("FailTest"));
    }
}
