package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;

/**
 * Lists all tutors or students in CLITutors to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all entries of tutors or students. "
            + "Parameters: "
            + "s/t";

    public static final String MESSAGE_SUCCESS_TUTOR = "Listed all tutors!";
    public static final String MESSAGE_SUCCESS_STUDENT = "Listed all students!";

    private PersonType personType;
    /**
     * Creates a ListCommand to list all of {@code PersonType}
     */
    public ListCommand(PersonType personType) {
        requireNonNull(personType);
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (personType) {
        case TUTOR:
            model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
            return new CommandResult(MESSAGE_SUCCESS_TUTOR);
        // No break necessary due to return statement
        case STUDENT:
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS_STUDENT);
        // No break necessary due to return statement
        default:
            // Any invalid input would be handled by the ClearCommandParser and will not reach here
            throw new CommandException(MESSAGE_USAGE);
        }
    }
}
