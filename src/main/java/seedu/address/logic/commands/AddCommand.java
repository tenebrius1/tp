package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutor or student to the database. "
            + "Parameters: "
            + "<s "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_REMARK + "REMARK] "
            + PREFIX_TAG + "TAG>"
            + " or <t "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_QUALIFICATION + "QUALIFICATION "
            + PREFIX_TAG + "TAG...>\n"
            + "Example: " + COMMAND_WORD + " t "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_GENDER + "M "
            + PREFIX_QUALIFICATION + "2 "
            + PREFIX_TAG + "PM";
    public static final String MESSAGE_SUCCESS_TUTOR = "New tutor added: %1$s";
    public static final String MESSAGE_SUCCESS_STUDENT = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_TUTOR = "This tutor already exists in the address book";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";

    private final Person toAdd;
    private final PersonType personType;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person, PersonType personType) {
        requireNonNull(person);
        requireNonNull(personType);
        toAdd = person;
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (personType) {
        case TUTOR:
            Tutor tutor = (Tutor) toAdd;
            if (model.hasTutor(tutor)) {
                throw new CommandException(MESSAGE_DUPLICATE_TUTOR);
            }

            model.addTutor(tutor);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TUTOR, tutor));
            // No break necessary due to return statement
        case STUDENT:
            Student student = (Student) toAdd;
            if (model.hasStudent(student)) {
                throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
            }

            model.addStudent(student);
            return new CommandResult(String.format(MESSAGE_SUCCESS_STUDENT, student));
            // No break necessary due to return statement
        default:
            // Any invalid input would be handled by the AddCommandParser and will not reach here
            throw new CommandException(MESSAGE_USAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd)
                && personType.equals(((AddCommand) other).personType));
    }
}
