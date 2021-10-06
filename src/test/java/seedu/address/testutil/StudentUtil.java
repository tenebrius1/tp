package seedu.address.testutil;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil extends PersonUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        return PersonUtil.getPersonDetails(student);
    }
}
