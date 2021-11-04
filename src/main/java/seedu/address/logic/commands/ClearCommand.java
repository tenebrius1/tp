package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.CliTutors;
import seedu.address.model.Model;

/**
 * Clears all tutor or student data in CLITutors.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all entries of tutors or students from the database\n "
            + "Parameters: "
            + "t/s\n"
            + "Example: clear s";

    public static final String MESSAGE_SUCCESS_TUTOR = "Tutor data has been cleared!";
    public static final String MESSAGE_SUCCESS_STUDENT = "Student data has been cleared!";

    private PersonType personType;

    // Used as a stub for testing purposes
    public ClearCommand() {
    }

    /**
     * Creates a ClearCommand to clear the {@code PersonType} data
     */
    public ClearCommand(PersonType personType) {
        requireNonNull(personType);
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (personType) {
        case TUTOR:
            model.setTutorData(new CliTutors());
            return new CommandResult(MESSAGE_SUCCESS_TUTOR);
            // No break necessary due to return statement
        case STUDENT:
            model.setStudentData(new CliTutors());
            return new CommandResult(MESSAGE_SUCCESS_STUDENT);
            // No break necessary due to return statement
        default:
            // Any invalid input would be handled by the ClearCommandParser and will not reach here
            throw new CommandException(MESSAGE_USAGE);
        }
    }
}
