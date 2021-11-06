package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.person.UniqueStudentList;
import seedu.address.model.person.UniqueTutorList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CliTutorsBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;
import seedu.address.testutil.TypicalPersons;

public class CliTutorsTest {
    private final CliTutors cliTutors = new CliTutors();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cliTutors.getTutorList());
        assertEquals(Collections.emptyList(), cliTutors.getStudentList());
        assertEquals(Collections.emptyList(), cliTutors.getMatchedTutorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.resetData(null));
    }

    @Test
    public void resetTutorData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.resetTutorData(null));
    }

    @Test
    public void resetStudentData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.resetStudentData(null));
    }

    @Test
    public void hasPersonWithSamePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.hasPersonWithSamePhone(null));
    }

    @Test
    public void resetData_withValidReadOnlyCliTutors_replacesData() {
        CliTutors newData = getTypicalCliTutors();
        cliTutors.resetData(newData);
        assertEquals(newData, cliTutors);
    }

    @Test
    public void resetTutorData_withValidReadOnlyCliTutors_replacesTutorDataOnly() {
        CliTutors newData = getTypicalCliTutors();
        ObservableList<Student> expectedStudentList = cliTutors.getStudentList();
        cliTutors.resetTutorData(newData);

        assertEquals(newData.getTutorList(), cliTutors.getTutorList());
        assertEquals(expectedStudentList, cliTutors.getStudentList());
    }

    @Test
    public void resetStudentData_withValidReadOnlyCliTutors_replacesStudentDataOnly() {
        CliTutors newData = getTypicalCliTutors();
        ObservableList<Tutor> expectedTutorList = cliTutors.getTutorList();
        cliTutors.resetStudentData(newData);

        assertEquals(newData.getStudentList(), cliTutors.getStudentList());
        assertEquals(expectedTutorList, cliTutors.getTutorList());
    }

    @Test
    public void resetData_withDuplicateTutors_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .build();
        List<Tutor> newTutors = Arrays.asList(ALICE, editedAlice);
        List<Student> newStudents = List.of(DANIEL);
        CliTutorsStub newData = new CliTutorsStub(newTutors, newStudents);

        assertThrows(DuplicatePersonException.class, () -> cliTutors.resetData(newData));
    }

    @Test
    public void hasTutor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.hasTutor(null));
    }

    @Test
    public void hasStudent_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.hasStudent(null));
    }

    @Test
    public void hasTutor_tutorNotInCliTutors_returnsFalse() {
        assertFalse(cliTutors.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentNotInCliTutors_returnsFalse() {
        assertFalse(cliTutors.hasStudent(DANIEL));
    }

    @Test
    public void hasTutor_tutorInCliTutors_returnsTrue() {
        cliTutors.addTutor(ALICE);
        assertTrue(cliTutors.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentInCliTutors_returnsTrue() {
        cliTutors.addStudent(DANIEL);
        assertTrue(cliTutors.hasStudent(DANIEL));
    }

    @Test
    public void hasTutor_tutorWithSameIdentityFieldsInCliTutors_returnsTrue() {
        cliTutors.addTutor(ALICE);
        Tutor editedAlice = new TutorBuilder(ALICE).withGender(VALID_GENDER_BOB).withTags(VALID_TAG_PM)
                .withQualification(VALID_QUALIFICATION_BOB).build();
        assertTrue(cliTutors.hasTutor(editedAlice));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInCliTutors_returnsTrue() {
        cliTutors.addStudent(DANIEL);
        Student editedDaniel = new StudentBuilder(DANIEL).withGender(VALID_GENDER_BOB).withTag(VALID_TAG_PM)
                .build();
        assertTrue(cliTutors.hasStudent(editedDaniel));
    }

    @Test
    public void getTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cliTutors.getTutorList().remove(0));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cliTutors.getStudentList().remove(0));
    }

    @Test
    public void getMatchedTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> cliTutors.getMatchedTutorList().remove(0));
    }

    @Test
    public void getTutorList_modifyList_success() {
        cliTutors.addTutor(ALICE);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(ALICE);
        assertEquals(expectedUniqueTutorList.asUnmodifiableObservableList(), cliTutors.getTutorList());
    }

    @Test
    public void getStudentList_modifyList_success() {
        cliTutors.addStudent(DANIEL);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(DANIEL);
        assertEquals(expectedUniqueStudentList.asUnmodifiableObservableList(), cliTutors.getStudentList());
    }

    @Test
    public void getMatchedTutorList_modifyList_success() {
        cliTutors.addTutor(ALICE);
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(ALICE);
        assertEquals(expectedUniqueTutorList.asUnmodifiableObservableList(), cliTutors.getMatchedTutorList());
    }

    @Test
    public void getMatchedTutorList_afterMatchCommand_success() {
        cliTutors.addTutor(ALICE);
        cliTutors.addTutor(BENSON);

        List<Tag> studentTagList = new ArrayList<>(ELLE.getTags());
        cliTutors.sortMatchedTutorList(studentTagList);

        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(BENSON);
        expectedUniqueTutorList.add(ALICE);
        assertEquals(expectedUniqueTutorList.asUnmodifiableObservableList(), cliTutors.getMatchedTutorList());
    }

    @Test
    public void sortMatchedTutorList_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cliTutors.sortMatchedTutorList(null));
    }

    @Test
    public void sortMatchedTutorList_validInputModifyTutorList_success() {
        TypicalPersons.getTypicalTutors().forEach(cliTutors::addTutor);
        List<Tag> studentTagList = new ArrayList<>(ELLE.getTags());
        UniqueTutorList expectedUniqueTutorList = new UniqueTutorList();
        expectedUniqueTutorList.add(BENSON);
        expectedUniqueTutorList.add(ALICE);
        expectedUniqueTutorList.add(CARL);
        cliTutors.sortMatchedTutorList(studentTagList);
        assertEquals(expectedUniqueTutorList.asUnmodifiableObservableList(), cliTutors.getMatchedTutorList());
    }

    @Test
    public void sortMatchedTutorList_validInputNoModification_success() {
        TypicalPersons.getTypicalTutors().forEach(cliTutors::addTutor);
        // Student chosen has no matching tags with any tutor
        List<Tag> studentTagList = new ArrayList<>(GEORGE.getTags());
        ObservableList<Tutor> expectedUniqueTutorList = cliTutors.getMatchedTutorList();
        cliTutors.sortMatchedTutorList(studentTagList);
        assertEquals(expectedUniqueTutorList, cliTutors.getMatchedTutorList());
    }

    @Test
    public void hasPersonWithSamePhoneTest() {
        TypicalPersons.getTypicalTutors().forEach(cliTutors::addTutor);
        TypicalPersons.getTypicalStudents().forEach(cliTutors::addStudent);

        assertTrue(cliTutors.hasPersonWithSamePhone(ALICE.getPhone()));

        Phone phone = new Phone("94351259");
        assertFalse(cliTutors.hasPersonWithSamePhone(phone));
    }

    @Test
    public void equals() {
        cliTutors.addTutor(ALICE);
        cliTutors.addStudent(DANIEL);
        CliTutors cliTutorsWithDifferentTutor =
                new CliTutorsBuilder().withTutor(BENSON).withStudent(DANIEL).build();
        CliTutors cliTutorsWithDifferentStudent =
                new CliTutorsBuilder().withTutor(ALICE).withStudent(IDA).build();

        // same object -> returns true
        assertEquals(cliTutors, cliTutors);

        // same values -> returns true
        CliTutors cliTutorsCopy = new CliTutors(cliTutors);
        assertEquals(cliTutors, cliTutorsCopy);

        // null -> returns false
        assertNotEquals(null, cliTutors);

        // different types -> returns false
        assertNotEquals(5, cliTutors);

        // different students -> return false
        assertNotEquals(cliTutors, cliTutorsWithDifferentStudent);

        // different tutors -> return false
        assertNotEquals(cliTutors, cliTutorsWithDifferentTutor);
    }

    /**
     * A stub ReadOnlyCliTutors whose persons list can violate interface constraints.
     */
    private static class CliTutorsStub implements ReadOnlyCliTutors {
        private final ObservableList<Tutor> tutors = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        CliTutorsStub(Collection<Tutor> tutors, Collection<Student> students) {
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
