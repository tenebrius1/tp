package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Changes the remark of an existing person in the address book.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches student identified by the index number used in the displayed student list with tutors "
            + "who teach the subjects the student wants\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MATCHED_SUCCESS = "Successfully matched %1$s";
    public static final String MESSAGE_MATCHED_FAILED = "Failed to match %1$s";

    private final Index index;

    /**
     * Creates a MatchCommand to match the specified student to tutors.
     *
     * @param index Index of the person in the filtered student list to match.
     */
    public MatchCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    /**
     * Executes a Match command.
     *
     * @param model Model to match tutor with.
     * @return A successful CommandResult with the students matched to tutors.
     * @throws CommandException An exception that occurs when matching students.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        // if displayed student list is empty
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT));
        }

        // if index is larger than displayed student list
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToMatch = lastShownList.get(index.getZeroBased());
        List<Tag> ls = new ArrayList<>(studentToMatch.getTags());
        model.updateMatchedTutor(new TagsContainTagPredicate(ls), ls, studentToMatch);

        // checks if there are any matched tutors
        if (model.getMatchedTutorList().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MATCHED_FAILED, studentToMatch.getName()));
        }

        return new CommandResult(String.format(MESSAGE_MATCHED_SUCCESS, studentToMatch.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && index.equals(((MatchCommand) other).index)); // state check
    }
}
