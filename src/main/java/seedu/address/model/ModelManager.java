package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
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
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CliTutors cliTutors;
    private final UserPrefs userPrefs;
    private final FilteredList<Tutor> filteredTutors;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Tutor> matchedTutors;

    /**
     * Initializes a ModelManager with the given cliTutors and userPrefs.
     */
    public ModelManager(ReadOnlyCliTutors cliTutors, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cliTutors, userPrefs);

        logger.fine("Initializing with address book: " + cliTutors + " and user prefs " + userPrefs);

        this.cliTutors = new CliTutors(cliTutors);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTutors = new FilteredList<>(this.cliTutors.getTutorList());
        filteredStudents = new FilteredList<>(this.cliTutors.getStudentList());
        matchedTutors = new FilteredList<>(this.cliTutors.getMatchedTutorList());
        matchedTutors.setPredicate(PREDICATE_SHOW_NO_PERSON);
    }

    public ModelManager() {
        this(new CliTutors(), new UserPrefs());
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
    public Path getCliTutorsFilePath() {
        return userPrefs.getCliTutorsFilePath();
    }

    @Override
    public void setCliTutorsFilePath(Path cliTutorsFilePath) {
        requireNonNull(cliTutorsFilePath);
        userPrefs.setCliTutorsFilePath(cliTutorsFilePath);
    }

    //=========== CliTutors ================================================================================

    @Override
    public void setCliTutors(ReadOnlyCliTutors cliTutors) {
        this.cliTutors.resetData(cliTutors);
    }

    @Override
    public void setTutorData(ReadOnlyCliTutors cliTutors) {
        this.cliTutors.resetTutorData(cliTutors);
    }

    @Override
    public void setStudentData(ReadOnlyCliTutors cliTutors) {
        this.cliTutors.resetStudentData(cliTutors);
    }

    @Override
    public ReadOnlyCliTutors getCliTutors() {
        return cliTutors;
    }

    @Override
    public boolean hasTutor(Tutor tutor) {
        requireNonNull(tutor);
        return cliTutors.hasTutor(tutor);
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return cliTutors.hasStudent(student);
    }

    @Override
    public void deleteTutor(Tutor target) {
        cliTutors.removeTutor(target);
    }

    @Override
    public void deleteStudent(Student target) {
        cliTutors.removeStudent(target);
    }

    @Override
    public void addTutor(Tutor tutor) {
        cliTutors.addTutor(tutor);
        updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
    }

    @Override
    public void addStudent(Student student) {
        cliTutors.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setTutor(Tutor target, Tutor editedTutor) {
        requireAllNonNull(target, editedTutor);

        cliTutors.setTutor(target, editedTutor);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        cliTutors.setStudent(target, editedStudent);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of tutors backed by the internal list of
     * {@code versionedCliTutors}
     */
    @Override
    public ObservableList<Tutor> getFilteredTutorList() {
        return filteredTutors;
    }

    /**
     * Returns an unmodifiable view of the list of students backed by the internal list of
     * {@code versionedCliTutors}
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
    public void updateMatchedTutor(Predicate<Person> predicate, List<Tag> studentTagList) {
        requireAllNonNull(predicate, studentTagList);
        matchedTutors.setPredicate(predicate);
        if (!matchedTutors.isEmpty()) {
            assert(!studentTagList.isEmpty()) : "studentTagList should not be empty at this point.";
            cliTutors.sortMatchedTutorList(studentTagList);
        }
    }

    @Override
    public void filterMatchedTutor(Predicate<Person> predicate) {
        requireNonNull(predicate);

        @SuppressWarnings("unchecked")
        Predicate<Person> matchingPredicate = (Predicate<Person>) matchedTutors.getPredicate();
        Predicate<Person> resultingPredicate = predicate.and(matchingPredicate);
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
        matchedTutors.setPredicate(builder.setPredicate(resultingPredicate).build());
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
        return cliTutors.equals(other.cliTutors)
                && userPrefs.equals(other.userPrefs)
                && filteredTutors.equals(other.filteredTutors)
                && filteredStudents.equals(other.filteredStudents)
                && matchedTutors.equals(other.matchedTutors);
    }
}
