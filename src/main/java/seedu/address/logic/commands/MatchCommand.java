package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
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
            + ": Matches Student identified by the index number used in the displayed student list with Tutors "
            + "who teach the subjects the Student wants.\n"
            + "Parameters: s/t INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MATCHED_SUCCESS = "Successfully matched %1$s";
    public static final String MESSAGE_MATCHED_FAILED = "Failed to match %1$s";

    private final Index index;

    /**
     * Creates a MatchCommand to match the specified student to tutors.
     *
     * @param Index of the person in the filtered student list to match.
     */
    public MatchCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return executeMatch(model);
    }

    /**
     * Executes a Match command.
     *
     * @param model Model to match tutor with.
     * @return A successful CommandResult with the students matched to tutors.
     * @throws CommandException An exception that occurs when matching students.
     */
    private CommandResult executeMatch(Model model) throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToMatch = lastShownList.get(index.getZeroBased());
        Tag studentTag = studentToMatch.getTag();
        ArrayList<Tag> ls = new ArrayList<>();
        ls.add(studentTag);
        model.updateMatchedTutor(new TagsContainTagPredicate(ls));

        if (model.getMatchedTutorList().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MATCHED_FAILED, studentToMatch));
        }

        return new CommandResult(String.format(MESSAGE_MATCHED_SUCCESS, studentToMatch));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && index.equals(((MatchCommand) other).index)); // state check
    }
}
