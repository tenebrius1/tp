package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedTutorFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                        PersonType.TUTOR);
        FindCommand expectedStudentFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Daniel")),
                        PersonType.STUDENT);
        assertParseSuccess(parser, "t n/Alice", expectedTutorFindCommand);

        // check for lowercase
        assertParseSuccess(parser, "t n/alice", expectedTutorFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t \n n/Alice", expectedTutorFindCommand);

        assertParseSuccess(parser, "s n/Daniel", expectedStudentFindCommand);

        // check for lowercase
        assertParseSuccess(parser, "s n/daniel", expectedStudentFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "s \n n/Daniel", expectedStudentFindCommand);
    }

}
