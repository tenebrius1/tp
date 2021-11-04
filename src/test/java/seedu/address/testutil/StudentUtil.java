package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.LETTER_DESC_STUDENT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.person.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil extends PersonUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + LETTER_DESC_STUDENT + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        return getPersonDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        return getEditPersonDescriptorDetails(descriptor);
    }
}
