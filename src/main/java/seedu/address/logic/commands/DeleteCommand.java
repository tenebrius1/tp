package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutor/student identified by the index number used in the displayed tutor/student list.\n"
            + "Parameters: s/t INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " t 1";

    public static final String MESSAGE_DELETE_TUTOR_SUCCESS = "Deleted Tutor: %1$s";
    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    private final Index targetIndex;
    private final PersonType personType;

    /**
     * Creates a DeleteCommand to delete the specified tutor/student.
     * @param targetIndex The specified index in either the tutor/student list, of the tutor/student to be deleted
     * @param personType The parsed personType (only a valid input if "t" or "s")
     */
    public DeleteCommand(Index targetIndex, PersonType personType) {
        this.targetIndex = targetIndex;
        this.personType = personType;
    }

    /**
     * Executes the delete command, which will remove a specified tutor/student from the list.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (personType) {
        case TUTOR:
            List<Tutor> lastShownTutorList = model.getFilteredTutorList();

            if (lastShownTutorList.isEmpty()) {
                throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, personType));
            }

            if (targetIndex.getZeroBased() >= lastShownTutorList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
            }

            Tutor tutorToDelete = lastShownTutorList.get(targetIndex.getZeroBased());
            model.deleteTutor(tutorToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TUTOR_SUCCESS, tutorToDelete));
            // No break necessary due to return statement
        case STUDENT:
            List<Student> lastShownStudentList = model.getFilteredStudentList();

            if (lastShownStudentList.isEmpty()) {
                throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, personType));
            }

            if (targetIndex.getZeroBased() >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }

            Student studentToDelete = lastShownStudentList.get(targetIndex.getZeroBased());
            model.deleteStudent(studentToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete));
            // No break necessary due to return statement
        default:
            // Any invalid input would be handled by the DeleteCommandParser and will not reach here
            throw new CommandException(MESSAGE_USAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
