package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

public class AddressBookTest {
    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTutorList());
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTutors_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .build();
        List<Tutor> newTutors = Arrays.asList(ALICE, editedAlice);
        List<Student> newStudents = List.of(DANIEL);
        AddressBookStub newData = new AddressBookStub(newTutors, newStudents);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasTutor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTutor(null));
    }

    @Test
    public void hasStudent_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasStudent(null));
    }

    @Test
    public void hasTutor_tutorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasStudent(DANIEL));
    }

    @Test
    public void hasTutor_tutorInAddressBook_returnsTrue() {
        addressBook.addTutor(ALICE);
        assertTrue(addressBook.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        addressBook.addStudent(DANIEL);
        assertTrue(addressBook.hasStudent(DANIEL));
    }

    @Test
    public void hasTutor_tutorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addTutor(ALICE);
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .withQualification(VALID_QUALIFICATION_BOB).build();
        assertTrue(addressBook.hasTutor(editedAlice));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addStudent(DANIEL);
        Student editedDaniel = new StudentBuilder(DANIEL).withGender(VALID_GENDER_BOB).withTag(VALID_TAG_PM)
                .build();
        assertTrue(addressBook.hasStudent(editedDaniel));
    }

    @Test
    public void getTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTutorList().remove(0));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Tutor> tutors = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<Tutor> tutors, Collection<Student> students) {
            this.tutors.setAll(tutors);
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Tutor> getTutorList() {
            return tutors;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }
}
