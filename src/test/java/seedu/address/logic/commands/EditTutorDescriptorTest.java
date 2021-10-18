package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_UNIVERSITY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditTutorDescriptorBuilder;

public class EditTutorDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTutorDescriptor descriptorWithSameValues =
                new EditCommand.EditTutorDescriptor(DESC_BOB);
        assertEquals(DESC_BOB, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_BOB, DESC_BOB);

        // null -> returns false
        assertNotEquals(null, DESC_BOB);

        // different types -> returns false
        assertNotEquals(5, DESC_BOB);

        // different person type -> returns false
        assertNotEquals(DESC_BOB, DESC_AMY);

        // different name -> returns false
        EditCommand.EditTutorDescriptor editedBob = new EditTutorDescriptorBuilder(DESC_BOB)
            .withName(VALID_NAME_AMY).build();
        assertNotEquals(DESC_BOB, editedBob);

        // different phone -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withPhone(VALID_PHONE_AMY).build();
        assertNotEquals(DESC_BOB, editedBob);

        // different gender -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withGender(VALID_GENDER_AMY).build();
        assertNotEquals(DESC_BOB, editedBob);

        // different qualification -> returns false
        editedBob =
                new EditTutorDescriptorBuilder(DESC_BOB)
                        .withQualification(VALID_QUALIFICATION_UNIVERSITY_STUDENT).build();
        assertNotEquals(DESC_BOB, editedBob);

        // different tags -> returns false
        editedBob = new EditTutorDescriptorBuilder(DESC_BOB).withTags(VALID_TAG_PM).build();
        assertNotEquals(DESC_BOB, editedBob);
    }
}
