package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEGER_MAX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEGER_MIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ZERO_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.PersonType.MESSAGE_INVALID_PERSON_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();
    private Index targetIndex = INDEX_FIRST_PERSON;
    private String validIndex = " " + targetIndex.getOneBased();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, VALID_TUTOR_LETTER + validIndex,
                new DeleteCommand(INDEX_FIRST_PERSON, PersonType.TUTOR));
        assertParseSuccess(parser, VALID_STUDENT_LETTER + validIndex,
                new DeleteCommand(INDEX_FIRST_PERSON, PersonType.STUDENT));
    }

    @Test
    public void parse_invalidPersonTypeButValidIndex_throwsParseException() {
        assertParseFailure(parser, INVALID_PREAMBLE + validIndex, String.format(MESSAGE_INVALID_PERSON_TYPE,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPersonTypeButInvalidNegativeIndex_throwsParseException() {
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INDEX,
                String.format(MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INDEX,
                String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPersonTypeButInvalidZeroIndex_throwsParseException() {
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_ZERO_INDEX,
                String.format(MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_ZERO_INDEX,
                String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPersonTypeButInvalidMaxIntIndex_throwsParseException() {
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INTEGER_MAX,
                String.format(MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INTEGER_MAX,
                String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPersonTypeButInvalidMinIntIndex_throwsParseException() {
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INTEGER_MIN,
                String.format(MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INTEGER_MIN,
                String.format(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {
        // too many arguments
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex + validIndex,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // too little arguments
        assertParseFailure(parser, VALID_STUDENT_LETTER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE));

        // no arguments
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE));
    }
}
