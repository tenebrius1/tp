package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT));
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToMatch = lastShownList.get(index.getZeroBased());
        Set<Tag> studentTag = studentToMatch.getTags();
        ArrayList<Tag> ls = new ArrayList<>();
        studentTag.stream().forEach(tag -> ls.add(tag));
        model.updateMatchedTutor(new TagsContainTagPredicate(ls));

        if (model.getMatchedTutorList().isEmpty()) {
            model.updateMatchedTutor(Model.PREDICATE_SHOW_NO_PERSON);
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
