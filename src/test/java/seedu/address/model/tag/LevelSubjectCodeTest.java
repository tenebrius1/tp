package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;

import org.junit.jupiter.api.Test;

class LevelSubjectCodeTest {
    @Test
    public void testIsValidTag() {
        assertTrue(LevelSubjectCode.isValidTag(VALID_TAG_PM));
        assertFalse(LevelSubjectCode.isValidTag(INVALID_TAG));
        assertFalse(LevelSubjectCode.isValidTag("Invalid"));
    }

    @Test
    public void testGetLabel() {
        assertEquals("PriMath", LevelSubjectCode.getLabel(VALID_TAG_PM));
        assertEquals("Invalid", LevelSubjectCode.getLabel("FailTest"));
    }

    @Test
    public void testGetSubCode() {
        assertEquals("pm", LevelSubjectCode.getSubCode("PriMath"));
        assertEquals("sc", LevelSubjectCode.getSubCode("SecChem"));
        assertEquals("Invalid", LevelSubjectCode.getSubCode("asdas"));
    }
}
