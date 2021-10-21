package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Tutor> filteredTutors;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Tutor> matchedTutors;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTutors = new FilteredList<>(this.addressBook.getTutorList());
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
        matchedTutors = new FilteredList<>(this.addressBook.getTutorList());
        matchedTutors.setPredicate(PREDICATE_SHOW_NO_TUTORS);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void setTutorData(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetTutorData(addressBook);
    }

    @Override
    public void setStudentData(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetStudentData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasTutor(Tutor tutor) {
        requireNonNull(tutor);
        return addressBook.hasTutor(tutor);
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return addressBook.hasStudent(student);
    }

    @Override
    public void deleteTutor(Tutor target) {
        addressBook.removeTutor(target);
    }

    @Override
    public void deleteStudent(Student target) {
        addressBook.removeStudent(target);
    }

    @Override
    public void addTutor(Tutor tutor) {
        addressBook.addTutor(tutor);
        updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
    }

    @Override
    public void addStudent(Student student) {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setTutor(Tutor target, Tutor editedTutor) {
        requireAllNonNull(target, editedTutor);

        addressBook.setTutor(target, editedTutor);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        addressBook.setStudent(target, editedStudent);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of tutors backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Tutor> getFilteredTutorList() {
        return filteredTutors;
    }

    /**
     * Returns an unmodifiable view of the list of students backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredTutorList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredTutors.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Matched Tutor List Accessors =============================================================

    @Override
    public void updateMatchedTutor(Predicate<Person> predicate) {
        requireNonNull(predicate);
        matchedTutors.setPredicate(predicate);
    }

    @Override
    public void filterMatchedTutor(Predicate<Person> predicate) {
        requireNonNull(predicate);
        matchedTutors.setPredicate(predicate);

//        @SuppressWarnings("unchecked")
//        Predicate<Person> matchingPredicate = (Predicate<Person>) matchedTutors.getPredicate();
//        Predicate<Person> resPredicate = predicate.and(matchingPredicate);
//        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
//        matchedTutors.setPredicate(builder.setPredicate(resPredicate).build());
    }

    @Override
    public ObservableList<Tutor> getMatchedTutorList() {
        return matchedTutors;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredTutors.equals(other.filteredTutors)
                && filteredStudents.equals(other.filteredStudents)
                && matchedTutors.equals(other.matchedTutors);
    }
}
