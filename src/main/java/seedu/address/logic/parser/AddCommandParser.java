package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Remark;
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
        requireNonNull(args);
        PersonType personType;
        try {
            personType = ParserUtil.parsePersonType(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), pe);
        }

        if (personType.equals(PersonType.TUTOR)) {
            return parseTutor(args);
        }

        if (personType.equals(PersonType.STUDENT)) {
            return parseStudent(args);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    private AddCommand parseTutor(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                        PREFIX_QUALIFICATION, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                PREFIX_QUALIFICATION, PREFIX_TAG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());

        Qualification qualification = ParserUtil.parseQualification(
                argMultimap.getValue(PREFIX_QUALIFICATION).get());

        Remark remark;
        // Handles remark if it exists
        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            remark = new Remark();
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Tutor tutor = new Tutor(name, phone, gender, qualification, remark, tagList);
        return new AddCommand(tutor, PersonType.TUTOR);
    }

    private AddCommand parseStudent(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                PREFIX_QUALIFICATION, PREFIX_REMARK, PREFIX_TAG);

        // Check if the user puts in qualification
        if (arePrefixesPresent(argMultimap, PREFIX_QUALIFICATION)) {
            throw new ParseException(MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER, PREFIX_TAG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());

        Remark remark;

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            remark = new Remark();
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Student student = new Student(name, phone, gender, remark, tagList);
        return new AddCommand(student, PersonType.STUDENT);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
