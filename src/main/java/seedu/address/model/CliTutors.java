package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.person.UniqueStudentList;
import seedu.address.model.person.UniqueTutorList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CliTutors implements ReadOnlyCliTutors {
    private final UniqueTutorList tutors;
    private final UniqueStudentList students;
    private final UniqueTutorList matchedTutors;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        tutors = new UniqueTutorList();
        students = new UniqueStudentList();
        matchedTutors = new UniqueTutorList();
    }

    public CliTutors() {}

    /**
     * Creates an CliTutors using the Persons in the {@code toBeCopied}
     */
    public CliTutors(ReadOnlyCliTutors toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the tutors list with {@code tutors}.
     * {@code tutors} must not contain duplicate persons.
     */
    public void setTutors(List<Tutor> tutors) {
        this.tutors.setTutors(tutors);
        this.matchedTutors.setTutors(tutors);
    }

    /**
     * Replaces the contents of the students list with {@code students}.
     * {@code students} must not contain duplicate persons.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code CliTutors} with {@code newData}.
     */
    public void resetData(ReadOnlyCliTutors newData) {
        requireNonNull(newData);

        setTutors(newData.getTutorList());
        setStudents(newData.getStudentList());
    }

    /**
     * Resets the existing tutor data of this {@code CliTutors} with that in {@code newData}.
     */
    public void resetTutorData(ReadOnlyCliTutors newData) {
        requireNonNull(newData);

        setTutors(newData.getTutorList());
    }

    /**
     * Resets the existing student data of this {@code CliTutors} with that in {@code newData}.
     */
    public void resetStudentData(ReadOnlyCliTutors newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// person-level operations

    /**
     * Returns true if a tutor with the same identity as {@code tutor} exists in the address book.
     */
    public boolean hasTutor(Tutor tutor) {
        requireNonNull(tutor);
        return tutors.contains(tutor);
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a tutor to the address book.
     * The tutor must not already exist in the address book.
     */
    public void addTutor(Tutor tutor) {
        tutors.add(tutor);
        matchedTutors.add(tutor);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Replaces the given tutor {@code target} in the list with {@code editedTutor}.
     * {@code target} must exist in the address book.
     * The tutor identity of {@code editedTutor} must not be the same as another existing tutor in the address book.
     */
    public void setTutor(Tutor target, Tutor editedTutor) {
        requireNonNull(editedTutor);

        tutors.setTutor(target, editedTutor);
        matchedTutors.setTutor(target, editedTutor);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code tutor} from this {@code CliTutors}.
     * {@code tutor} must exist in the address book.
     */
    public void removeTutor(Tutor tutor) {
        tutors.remove(tutor);
        matchedTutors.remove(tutor);
    }

    /**
     * Removes {@code student} from this {@code CliTutors}.
     * {@code student} must exist in the address book.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Sorts tutors based on number of matching tags with ls in descending order.
     * @param studentTagList List of Tags used to compare with each tutor.
     */
    public void sortMatchedTutorList(List<Tag> studentTagList) {
        requireNonNull(studentTagList);
        matchedTutors.sortTutors(studentTagList);
    }

    //// util methods

    @Override
    public String toString() {
        return tutors.asUnmodifiableObservableList().size() + " tutors, "
                + students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Tutor> getTutorList() {
        return tutors.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    public ObservableList<Tutor> getMatchedTutorList() {
        return matchedTutors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CliTutors // instanceof handles nulls
                && tutors.equals(((CliTutors) other).tutors)
                && students.equals(((CliTutors) other).students))
                && matchedTutors.equals(((CliTutors) other).matchedTutors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutors, students);
    }
}
