package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.PersonType.STUDENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all entries of tutors/students from the database. "
            + "Parameters: "
            + "s/t";

    public static final String MESSAGE_SUCCESS_TUTOR = "Tutor data has been cleared!";
    public static final String MESSAGE_SUCCESS_STUDENT = "Student data has been cleared!";

    private PersonType personType;

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
            model.setTutorData(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS_TUTOR);
            // No break necessary due to return statement
        case STUDENT:
            model.setStudentData(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS_STUDENT);
            // No break necessary due to return statement
        default:
            model.setAddressBook(new AddressBook());
            // Any invalid input would be handled by the ClearCommandParser and will not reach here
            throw new CommandException(MESSAGE_USAGE);
        }
    }
}
