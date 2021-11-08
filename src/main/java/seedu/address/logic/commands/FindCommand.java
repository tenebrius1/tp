package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Finds and lists all students or tutors in address book whose name contains the argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all student/tutor whose names contain "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: <t "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_QUALIFICATION + "QUALIFICATION] "
            + "[" + PREFIX_TAG + "TAG...]>\n"
            + "or <s "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_TAG + "TAG...]>\n"
            + "Example: " + COMMAND_WORD + " t n/charlie";

    private final Predicate<Person> predicate;
    private final PersonType personType;

    /** Creates a FindCommand to search for the specified {@code Person} */
    public FindCommand(Predicate<Person> predicate, PersonType personType) {
        requireNonNull(predicate);
        requireNonNull(personType);
        this.predicate = predicate;
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (personType) {
        case TUTOR:
            return executeFindTutor(model);
            // No break necessary due to return statement
        case STUDENT:
            return executeFindStudent(model);
            // No break necessary due to return statement
        default:
            // Any invalid input would be handled by the FindCommandParser and will not reach here
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
    }

    private CommandResult executeFindStudent(Model model) throws CommandException {
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        List<Student> studentList = model.getFilteredStudentList();
        // Checks if studentList is empty
        if (studentList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT));
        }

        model.updateFilteredStudentList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                model.getFilteredStudentList().size()));
    }

    private CommandResult executeFindTutor(Model model) throws CommandException {
        model.updateFilteredTutorList(Model.PREDICATE_SHOW_ALL_TUTORS);
        List<Tutor> tutorList = model.getFilteredTutorList();
        // Checks if tutorList is empty
        if (tutorList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.TUTOR));
        }

        model.updateFilteredTutorList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_TUTORS_LISTED_OVERVIEW,
                model.getFilteredTutorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate) // state check
                && personType == ((FindCommand) other).personType); // type check
    }
}
