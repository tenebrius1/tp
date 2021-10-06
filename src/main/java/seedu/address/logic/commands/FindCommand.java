package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students or tutors in address book whose name contains the argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all student/tutor whose names contain "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " t n/charlie";

    private final NameContainsKeywordsPredicate predicate;
    private final PersonType personType;

    /** Creates a FindCommand to search for the specified {@code Person} */
    public FindCommand(NameContainsKeywordsPredicate predicate, PersonType personType) {
        requireNonNull(predicate);
        requireNonNull(personType);
        this.predicate = predicate;
        this.personType = personType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (personType == PersonType.TUTOR) {
            model.updateFilteredTutorList(predicate);
            return new CommandResult(String.format(Messages.MESSAGE_TUTORS_LISTED_OVERVIEW,
                    model.getFilteredTutorList().size()));
        } else if (personType == PersonType.STUDENT) {
            model.updateFilteredStudentList(predicate);
            return new CommandResult(String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                    model.getFilteredStudentList().size()));
        } else {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)) // state check
                && personType == ((FindCommand) other).personType; // type check
    }
}
