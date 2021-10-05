package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.AMY;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DANIEL.isSamePerson(DANIEL));

        // null -> returns false
        assertFalse(DANIEL.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Student editedDaniel = new StudentBuilder(DANIEL).withPhone(VALID_PHONE_AMY).withGender(VALID_GENDER_AMY)
                .withTag(VALID_TAG_PM).build();
        assertTrue(DANIEL.isSamePerson(editedDaniel));

        // different name, all other attributes same -> returns false
        editedDaniel = new StudentBuilder(DANIEL).withName(VALID_NAME_AMY).build();
        assertFalse(DANIEL.isSamePerson(editedDaniel));

        // name differs in case, all other attributes same -> returns false
        Student editedAmy = new StudentBuilder(AMY).withName(VALID_NAME_AMY.toLowerCase()).build();
        assertFalse(AMY.isSamePerson(editedAmy));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_AMY + " ";
        editedAmy = new StudentBuilder(AMY).withName(nameWithTrailingSpaces).build();
        assertFalse(AMY.isSamePerson(editedAmy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(DANIEL).build();
        assertTrue(DANIEL.equals(aliceCopy));

        // same object -> returns true
        assertTrue(DANIEL.equals(DANIEL));

        // null -> returns false
        assertFalse(DANIEL.equals(null));

        // different type -> returns false
        assertFalse(DANIEL.equals(5));

        // different student -> returns false
        assertFalse(DANIEL.equals(AMY));

        // different name -> returns false
        Student editedDaniel = new StudentBuilder(DANIEL).withName(VALID_NAME_AMY).build();
        assertFalse(DANIEL.equals(editedDaniel));

        // different phone -> returns false
        editedDaniel = new StudentBuilder(DANIEL).withPhone(VALID_PHONE_AMY).build();
        assertFalse(DANIEL.equals(editedDaniel));

        // different gender -> returns false
        editedDaniel = new StudentBuilder(DANIEL).withGender(VALID_GENDER_AMY).build();
        assertFalse(DANIEL.equals(editedDaniel));

        // different tags -> returns false
        editedDaniel = new StudentBuilder(DANIEL).withTag(VALID_TAG_PM).build();
        assertFalse(DANIEL.equals(editedDaniel));
    }
}
