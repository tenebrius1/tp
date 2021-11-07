package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MatchCommand} object
 */
public class MatchCommandParser implements Parser<MatchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MatchCommand}
     * and returns a {@code MatchCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        String preamble = argMultimap.getPreamble().trim();

        // Check if argument passed is a number or not
        if (!StringUtil.isNumeric(preamble)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new MatchCommand(index);
        } catch (ParseException pe) {
            // Catches Non Integer value passed as argument
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, pe);
        }
    }
}
