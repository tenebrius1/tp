package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches Student identified by the index number used in the displayed student list with Tutors "
            + " who teach the subjects the Student wants.\n"
            + "Parameters: s/t INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MATCHED_SUCCESS = "Successfully matched %1$s";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * Creates a MatchCommand to match the specified student to tutors.
     *
     * @param index of the person in the filtered student list to match
     */
    public MatchCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && index.equals(((MatchCommand) other).index)); // state check
    }
}
