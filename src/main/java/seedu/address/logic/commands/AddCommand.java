package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutor or student to the database. "
            + "Parameters: "
            + "t "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_GENDER + "M "
            + PREFIX_QUALIFICATION + "2"
            + PREFIX_TAG + "PM";
    public static final String MESSAGE_SUCCESS_TUTOR = "New tutor added: %1$s";
    public static final String MESSAGE_SUCCESS_STUDENT = "New student added: %1$s";
    public static final String MESSAGE_TOO_MANY_TAGS = "Students can only have 1 tag.";
    public static final String MESSAGE_DUPLICATE_TUTOR = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This person already exists in the address book";

    private static final String tutor = "t";
    private static final String student = "s";

    private final Person toAdd;
    private final String personType;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person, String personType) {
        requireNonNull(person);
        requireNonNull(personType);
        toAdd = person;
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (personType.equals(tutor)) {
            Tutor tutor = (Tutor) toAdd;
            if (model.hasTutor(tutor)) {
                throw new CommandException(MESSAGE_DUPLICATE_TUTOR);
            }

            model.addTutor(tutor);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TUTOR, tutor));
        }

        if (personType.equals(student)) {
            Student student = (Student) toAdd;
            if (model.hasStudent(student)) {
                throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
            }

            model.addStudent(student);
            return new CommandResult(String.format(MESSAGE_SUCCESS_STUDENT, tutor));
        }

        //Any invalid input would be handled by the AddCommandParser and will not reach here
        throw new CommandException(MESSAGE_USAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

}
