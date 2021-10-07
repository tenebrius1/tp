package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTutorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PersonType personType = ParserUtil.parsePersonType(args);
        switch (personType) {
        case TUTOR:
            ArgumentMultimap tutorArgMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER,
                            PREFIX_QUALIFICATION, PREFIX_TAG);

            EditTutorDescriptor editTutorDescriptor = new EditTutorDescriptor();
            return parsePerson(tutorArgMultimap, editTutorDescriptor, personType);
            // No break necessary due to return statement
        case STUDENT:
            ArgumentMultimap studentMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER, PREFIX_TAG);

            EditTutorDescriptor editStudentDescriptor = new EditTutorDescriptor();
            return parsePerson(studentMultimap, editStudentDescriptor, personType);
            // No break necessary due to return statement
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    private EditCommand parsePerson(ArgumentMultimap argMultimap, EditCommand.EditPersonDescriptor editPersonDescriptor,
                                    PersonType personType) throws ParseException {
        Index personIndex;

        try {
            String trimmed = argMultimap.getPreamble().trim();
            String[] split = trimmed.split(" ", 3);
            // If personType or index not given, it is an invalid command format. ArrayIndexOutOfBoundsException
            // will be thrown by these lines below, but we catch it and throw a ParseException
            if (split.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            personIndex = ParserUtil.parseIndex(split[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (personType.equals(PersonType.TUTOR) && argMultimap.getValue(PREFIX_QUALIFICATION).isPresent()) {
            EditTutorDescriptor x = (EditTutorDescriptor) editPersonDescriptor;
            x.setQualification(ParserUtil.parseQualification(
                    argMultimap.getValue(PREFIX_QUALIFICATION).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(personIndex, editPersonDescriptor, personType);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
