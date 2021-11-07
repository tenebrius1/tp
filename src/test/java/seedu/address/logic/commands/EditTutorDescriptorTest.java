package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_UNIVERSITY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditTutorDescriptorBuilder;

public class EditTutorDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTutorDescriptor descriptorWithSameValues =
                new EditCommand.EditTutorDescriptor(DESC_BOB);
        assertTrue(DESC_BOB.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BOB.equals(DESC_BOB));

        // null -> returns false
        assertFalse(DESC_BOB.equals(null));

        // different types -> returns false
        assertFalse(DESC_BOB.equals(5));

        // different person type -> returns false
        assertFalse(DESC_BOB.equals(DESC_AMY));

        // different name -> returns false
        EditCommand.EditTutorDescriptor editedBob = new EditTutorDescriptorBuilder(DESC_BOB)
            .withName(VALID_NAME_AMY).build();
        assertFalse(DESC_BOB.equals(editedBob));

        // different phone -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withPhone(VALID_PHONE_AMY).build();
        assertFalse(DESC_BOB.equals(editedBob));

        // different gender -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withGender(VALID_GENDER_AMY).build();
        assertFalse(DESC_BOB.equals(editedBob));

        // different qualification -> returns false
        editedBob =
                new EditTutorDescriptorBuilder(DESC_BOB)
                        .withQualification(VALID_QUALIFICATION_UNIVERSITY_STUDENT).build();
        assertFalse(DESC_BOB.equals(editedBob));

        // different remark -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withRemark(VALID_REMARK_AMY).build();
        assertFalse(DESC_BOB.equals(editedBob));

        // different tags -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withTags(VALID_TAG_PM).build();
        assertFalse(DESC_BOB.equals(editedBob));
    }
}
