package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        PersonType personType = ParserUtil.parsePersonType(args);
        switch (personType) {
        case TUTOR:
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                            PREFIX_QUALIFICATION, PREFIX_TAG);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                    PREFIX_QUALIFICATION, PREFIX_TAG)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
            Qualification qualification = ParserUtil.parseQualification(
                    argMultimap.getValue(PREFIX_QUALIFICATION).get());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Tutor tutor = new Tutor(name, phone, gender, qualification, tagList);
            return new AddCommand(tutor, PersonType.TUTOR);
            // No break necessary due to return statement
        case STUDENT:
            argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                    PREFIX_QUALIFICATION, PREFIX_TAG);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                    PREFIX_QUALIFICATION, PREFIX_TAG)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            //Check if tagList has more than one tag for students
            if (tagList.size() != 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_TOO_MANY_TAGS));
            }

            Tag tag = tagList.iterator().next();

            Student student = new Student(name, phone, gender, tag);
            return new AddCommand(student, PersonType.STUDENT);
            // No break necessary due to return statement
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
