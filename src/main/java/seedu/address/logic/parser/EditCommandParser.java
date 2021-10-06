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
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
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

            Index tutorIndex;

            try {
                tutorIndex = ParserUtil.parseIndex(tutorArgMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
            }

            EditTutorDescriptor editTutorDescriptor = new EditTutorDescriptor();
            if (tutorArgMultimap.getValue(PREFIX_NAME).isPresent()) {
                editTutorDescriptor.setName(ParserUtil.parseName(tutorArgMultimap.getValue(PREFIX_NAME).get()));
            }
            if (tutorArgMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editTutorDescriptor.setPhone(ParserUtil.parsePhone(tutorArgMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (tutorArgMultimap.getValue(PREFIX_GENDER).isPresent()) {
                editTutorDescriptor.setGender(ParserUtil.parseGender(tutorArgMultimap.getValue(PREFIX_GENDER).get()));
            }
            if (tutorArgMultimap.getValue(PREFIX_QUALIFICATION).isPresent()) {
                editTutorDescriptor.setQualification(ParserUtil.parseQualification(
                        tutorArgMultimap.getValue(PREFIX_QUALIFICATION).get()));
            }
            parseTagsForEdit(tutorArgMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTutorDescriptor::setTags);

            if (!editTutorDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(tutorIndex, editTutorDescriptor, personType);
        case STUDENT:
            ArgumentMultimap studentArgMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER, PREFIX_TAG);

            Index studentIndex;

            try {
                studentIndex = ParserUtil.parseIndex(studentArgMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
            }

            EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
            if (studentArgMultimap.getValue(PREFIX_GENDER).isPresent()) {
                editStudentDescriptor.setGender(ParserUtil.parseGender(
                    studentArgMultimap.getValue(PREFIX_GENDER).get()));
            }
            if (studentArgMultimap.getValue(PREFIX_NAME).isPresent()) {
                editStudentDescriptor.setName(ParserUtil.parseName(studentArgMultimap.getValue(PREFIX_NAME).get()));
            }
            if (studentArgMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editStudentDescriptor.setPhone(ParserUtil.parsePhone(studentArgMultimap.getValue(PREFIX_PHONE).get()));
            }
            parseTagsForEdit(studentArgMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStudentDescriptor::setTags);

            if (!editStudentDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(studentIndex, editStudentDescriptor, personType);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
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
