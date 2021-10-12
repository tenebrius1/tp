package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_TUTORS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Person> PREDICATE_SHOW_NO_TUTORS = unused -> false;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a tutor with the same identity as {@code tutor} exists in the address book.
     */
    boolean hasTutor(Tutor tutor);

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given tutor.
     * The tutor must exist in the address book.
     */
    void deleteTutor(Tutor tutor);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student student);

    /**
     * Adds the given tutor.
     * {@code tutor} must not already exist in the address book.
     */
    void addTutor(Tutor tutor);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given tutor {@code target} with {@code editedTutor}.
     * {@code target} must exist in the address book.
     * The tutor identity of {@code editedTutor} must not be the same as another existing tutor in the address book.
     */
    void setTutor(Tutor target, Tutor editedTutor);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the address book.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered tutor list */
    ObservableList<Tutor> getFilteredTutorList();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered tutor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Person> predicate);

    /**
     * Updates the filter of the matched tutors list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateMatchedTutor(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the matched tutor list */
    ObservableList<Tutor> getMatchedTutorList();
}
