package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the tutors list.
     * This list will not contain any duplicate tutors.
     */
    ObservableList<Tutor> getTutorList();

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();
}
