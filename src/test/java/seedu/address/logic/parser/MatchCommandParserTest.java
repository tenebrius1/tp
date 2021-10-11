package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ZERO_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchCommand;

public class MatchCommandParserTest {
    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased());
        MatchCommand expectedCommand = new MatchCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

        // no index
        assertParseFailure(parser, MatchCommand.COMMAND_WORD , expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, INVALID_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, INVALID_ZERO_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        Index targetIndex = INDEX_SECOND_PERSON;
        assertParseFailure(parser, INVALID_PREAMBLE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));
    }
}