package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public DeleteCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        String[] split = trimmed.split(" ");
        try {
            if (split.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            // If personType or index not given, it is an invalid command format. ParseException with
            // MESSAGE_INVALID_INDEX will be thrown by the line below, but we catch it and throw a more specific and
            // non-technical ParseException below.
            PersonType personType = ParserUtil.parsePersonType(split[0]);
            Index index = ParserUtil.parseIndex(split[1]);
            return new DeleteCommand(index, personType);
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_INVALID_INDEX)) {
                // The personType must be valid since the parsePersonType statement did not throw a ParseException
                PersonType personType = ParserUtil.parsePersonType(split[0]);
                switch(personType) {
                case STUDENT:
                    throw new ParseException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
                case TUTOR:
                    throw new ParseException(MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
                default:
                    throw pe;
                }
            } else {
                throw pe;
            }
        }
    }
}
