package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullTutorOrStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null, PersonType.TUTOR));
        assertThrows(NullPointerException.class, () -> new AddCommand(null, PersonType.STUDENT));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTutorAdded modelStubTutor = new ModelStubAcceptingTutorAdded();
        ModelStubAcceptingStudentAdded modelStubStudent = new ModelStubAcceptingStudentAdded();
        Tutor validTutor = new TutorBuilder().build();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResultTutor = new AddCommand(validTutor, PersonType.TUTOR).execute(modelStubTutor);
        CommandResult commandResultStudent = new AddCommand(validStudent, PersonType.STUDENT).execute(modelStubStudent);

        // Adding a tutor
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_TUTOR, validTutor),
                commandResultTutor.getFeedbackToUser());
        assertEquals(Arrays.asList(validTutor), modelStubTutor.tutorsAdded);

        // Adding a student
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, validStudent),
                commandResultStudent.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStubStudent.studentsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Tutor validTutor = new TutorBuilder().build();
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommandTutor = new AddCommand(validTutor, PersonType.TUTOR);
        AddCommand addCommandStudent = new AddCommand(validStudent, PersonType.STUDENT);
        ModelStub modelStubTutor = new ModelStubWithTutor(validTutor);
        ModelStub modelStubStudent = new ModelStubWithStudent(validStudent);

        // Check for duplicate tutor
        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_TUTOR, () ->
                addCommandTutor.execute(modelStubTutor));

        // Check for duplicate student
        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STUDENT, () ->
                addCommandStudent.execute(modelStubStudent));
    }

    @Test
    public void equals() {
        Tutor tutorAlice = new TutorBuilder().withName("Alice").build();
        Tutor tutorBob = new TutorBuilder().withName("Bob").build();

        Student studentCharlie = new StudentBuilder().withName("Charlie").build();

        AddCommand addAliceCommand = new AddCommand(tutorAlice, PersonType.TUTOR);
        AddCommand addBobCommand = new AddCommand(tutorBob, PersonType.TUTOR);
        AddCommand addCharlieCommand = new AddCommand(studentCharlie, PersonType.STUDENT);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(tutorAlice, PersonType.TUTOR);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different tutor -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        // different class (student) -> returns false
        assertFalse(addAliceCommand.equals(addCharlieCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutor(Tutor tutor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent (Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutor(Tutor tutor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutor(Tutor tutor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutor(Tutor target, Tutor editedTutor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tutor> getFilteredTutorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTutorList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMatchedTutor(Predicate<Person> predicate, List<Tag> ls) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void filterMatchedTutor(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tutor> getMatchedTutorList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single tutor.
     */
    private class ModelStubWithTutor extends ModelStub {
        private final Tutor tutor;

        ModelStubWithTutor(Tutor tutor) {
            requireNonNull(tutor);
            this.tutor = tutor;
        }

        @Override
        public boolean hasTutor(Tutor tutor) {
            requireNonNull(tutor);
            return this.tutor.isSamePerson(tutor);
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSamePerson(student);
        }
    }

    /**
     * A Model stub that always accepts the tutor being added.
     */
    private class ModelStubAcceptingTutorAdded extends ModelStub {
        final ArrayList<Tutor> tutorsAdded = new ArrayList<>();

        @Override
        public boolean hasTutor(Tutor tutor) {
            requireNonNull(tutor);
            return tutorsAdded.stream().anyMatch(tutor::isSamePerson);
        }

        @Override
        public void addTutor(Tutor tutor) {
            requireNonNull(tutor);
            tutorsAdded.add(tutor);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accepts the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSamePerson);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
