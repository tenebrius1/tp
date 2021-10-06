package seedu.address.testutil;

import seedu.address.logic.parser.PersonType;
import seedu.address.model.person.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil extends PersonUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return PersonUtil.getAddCommand(student, PersonType.STUDENT);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        return PersonUtil.getPersonDetails(student);
    }
}
