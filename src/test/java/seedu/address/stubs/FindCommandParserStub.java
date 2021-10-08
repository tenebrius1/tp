package seedu.address.stubs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Acts as a stub for FindCommandParser for testing purposes.
 */
public class FindCommandParserStub {
    /**
     * Extracts the name to search for in FindCommand.
     */
    public static Name extractName(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElseThrow(
                () -> new ParseException(Messages.MESSAGE_INVALID_COMMAND_FORMAT)));
    }
}
