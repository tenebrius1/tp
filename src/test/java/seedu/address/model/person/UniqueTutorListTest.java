package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TutorBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

public class UniqueTutorListTest {

    private final UniqueTutorList uniqueTutorList = new UniqueTutorList();

    @Test
    public void contains_nullTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.contains(null));
    }

    @Test
    public void contains_tutorNotInList_returnsFalse() {
        assertFalse(uniqueTutorList.contains(ALICE));
    }

    @Test
    public void contains_tutorInList_returnsTrue() {
        uniqueTutorList.add(ALICE);
        assertTrue(uniqueTutorList.contains(ALICE));
    }

    @Test
    public void contains_tutorWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTutorList.add(ALICE);
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .build();
        assertTrue(uniqueTutorList.contains(editedAlice));
    }

    @Test
    public void add_nullTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.add(null));
    }

    @Test
    public void add_duplicateTutor_throwsDuplicateTutorException() {
        uniqueTutorList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueTutorList.add(ALICE));
    }

    @Test
    public void setTutor_nullTargetTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.setTutor(null, ALICE));
    }

    @Test
    public void setTutor_nullEditedTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.setTutor(ALICE, null));
    }

    @Test
    public void setTutor_targetTutorNotInList_throwsTutorNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueTutorList.setTutor(ALICE, ALICE));
    }

    @Test
    public void setTutor_editedTutorIsSameTutor_success() {
        uniqueTutorList.add(ALICE);
        uniqueTutorList.setTutor(ALICE, ALICE);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(ALICE);
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutor_editedTutorHasSameIdentity_success() {
        uniqueTutorList.add(ALICE);
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .build();
        uniqueTutorList.setTutor(ALICE, editedAlice);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(editedAlice);
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutor_editedTutorHasDifferentIdentity_success() {
        uniqueTutorList.add(ALICE);
        uniqueTutorList.setTutor(ALICE, BOB);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(BOB);
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutor_editedTutorHasNonUniqueIdentity_throwsDuplicateTutorException() {
        uniqueTutorList.add(ALICE);
        uniqueTutorList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueTutorList.setTutor(ALICE, BOB));
    }

    @Test
    public void remove_nullTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.remove(null));
    }

    @Test
    public void remove_tutorDoesNotExist_throwsTutorNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueTutorList.remove(ALICE));
    }

    @Test
    public void remove_existingTutor_removesTutor() {
        uniqueTutorList.add(ALICE);
        uniqueTutorList.remove(ALICE);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutors_nullUniqueTutorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.setTutors((UniqueTutorList) null));
    }

    @Test
    public void setTutors_uniqueTutorList_replacesOwnListWithProvidedUniqueTutorList() {
        uniqueTutorList.add(ALICE);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(BOB);
        uniqueTutorList.setTutors(expectedUniqueTutorList);
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutors_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorList.setTutors((List<Tutor>) null));
    }

    @Test
    public void setTutors_list_replacesOwnListWithProvidedList() {
        uniqueTutorList.add(ALICE);
        List<Tutor> tutorList = Collections.singletonList(BOB);
        uniqueTutorList.setTutors(tutorList);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(BOB);
        assertEquals(expectedUniqueTutorList, uniqueTutorList);
    }

    @Test
    public void setTutors_listWithDuplicateTutors_throwsDuplicateTutorException() {
        List<Tutor> listWithDuplicateTutors = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueTutorList.setTutors(listWithDuplicateTutors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueTutorList.asUnmodifiableObservableList().remove(0));
    }

}