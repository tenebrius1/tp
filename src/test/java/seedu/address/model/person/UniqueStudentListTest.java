package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalPersons;

public class UniqueStudentListTest {
    private final UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(DANIEL));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(DANIEL);
        assertTrue(uniqueStudentList.contains(DANIEL));
    }

    @Test
    public void contains_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(DANIEL);
        Student editedDaniel = new StudentBuilder(DANIEL).withGender(VALID_GENDER_AMY).withTag(VALID_TAG_PM)
                .build();
        assertTrue(uniqueStudentList.contains(editedDaniel));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(DANIEL);
        assertThrows(DuplicatePersonException.class, () -> uniqueStudentList.add(DANIEL));
    }

    @Test
    public void setStudent_nullTargetStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudent(null, DANIEL));
    }

    @Test
    public void setStudent_nullEditedStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudent(DANIEL, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueStudentList.setStudent(DANIEL, DANIEL));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(DANIEL);
        uniqueStudentList.setStudent(DANIEL, DANIEL);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(DANIEL);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasSameIdentity_success() {
        uniqueStudentList.add(DANIEL);
        Student editedDaniel = new StudentBuilder(DANIEL).withGender(VALID_GENDER_AMY).withTag(VALID_TAG_PM)
                .build();
        uniqueStudentList.setStudent(DANIEL, editedDaniel);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(editedDaniel);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasDifferentIdentity_success() {
        uniqueStudentList.add(DANIEL);
        uniqueStudentList.setStudent(DANIEL, AMY);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(AMY);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(DANIEL);
        uniqueStudentList.add(AMY);
        assertThrows(DuplicatePersonException.class, () -> uniqueStudentList.setStudent(DANIEL, AMY));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsStudentNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueStudentList.remove(DANIEL));
    }

    @Test
    public void remove_existingStudent_removesStudent() {
        uniqueStudentList.add(DANIEL);
        uniqueStudentList.remove(DANIEL);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullUniqueStudentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((UniqueStudentList) null));
    }

    @Test
    public void setStudents_uniqueStudentList_replacesOwnListWithProvidedUniqueStudentList() {
        uniqueStudentList.add(DANIEL);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(AMY);
        uniqueStudentList.setStudents(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(DANIEL);
        List<Student> studentList = Collections.singletonList(AMY);
        uniqueStudentList.setStudents(studentList);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(AMY);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(DANIEL, DANIEL);
        assertThrows(DuplicatePersonException.class, () -> uniqueStudentList.setStudents(listWithDuplicateStudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStudentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        List<Student> studentList = new ArrayList<>();
        studentList.addAll(TypicalPersons.getTypicalStudents());
        uniqueStudentList.setStudents(studentList);

        UniqueStudentList uniqueStudentListCopy = new UniqueStudentList();
        uniqueStudentListCopy.setStudents(studentList);
        assertTrue(uniqueStudentListCopy.equals(uniqueStudentList));

        // same object -> returns true
        assertTrue(uniqueStudentList.equals(uniqueStudentList));

        // null -> returns false
        assertFalse(uniqueStudentList.equals(null));

        // different type -> returns false
        assertFalse(uniqueStudentList.equals(5));

        // different list -> returns false
        UniqueStudentList uniqueStudentListDifferent = new UniqueStudentList();
        uniqueStudentListDifferent.add(DANIEL);
        assertFalse(uniqueStudentList.equals(uniqueStudentListDifferent));
    }
}
