package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_UNIVERSITY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TutorBuilder;

public class TutorTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Tutor tutor = new TutorBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> tutor.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone, all other attributes different -> returns true
        Tutor editedAlice = new TutorBuilder(ALICE).withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_PM).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same -> returns false
        editedAlice = new TutorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void isRemarkEmptyTest() {
        String remark = "";
        // empty remark
        Tutor editedAlice = new TutorBuilder(ALICE).withRemark(remark).build();
        assertTrue(editedAlice.isRemarkEmpty());

        // valid remark
        remark = "remark";
        editedAlice = new TutorBuilder(ALICE).withRemark(remark).build();
        assertFalse(editedAlice.isRemarkEmpty());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tutor aliceCopy = new TutorBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different tutor -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Tutor editedAlice = new TutorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TutorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different gender -> returns false
        editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different qualification -> returns false
        editedAlice =
                new TutorBuilder(ALICE).withQualification(VALID_QUALIFICATION_UNIVERSITY_STUDENT).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TutorBuilder(ALICE).withTags(VALID_TAG_TP).build();
        assertFalse(ALICE.equals(editedAlice));

        // different remarks -> returns false
        editedAlice = new TutorBuilder(ALICE).withRemark(VALID_REMARK_AMY).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
