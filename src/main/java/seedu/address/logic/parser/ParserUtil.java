package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.tag.LevelSubjectCode;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code String args} and extracts out the type of Person (Tutor/Student).
     *
     * @return Returns the string at the index of args.
     */
    public static PersonType parsePersonType(String args) throws ParseException {
        // Allows for command to be valid even with multiple whitespaces within the command.
        // For e.g. "add    t   n/..." will be a valid command read as "add t n/...".
        String formattedString = args.replaceAll("\\s{2,}", " ").trim();
        String[] parsedString = formattedString.split(" ");
        String personType = (String) Array.get(parsedString, 0);
        return PersonType.detectPersonType(personType);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        checkNullInput(oneBasedIndex, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        checkNullInput(gender, Gender.MESSAGE_CONSTRAINTS);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        checkNullInput(name, Name.MESSAGE_CONSTRAINTS);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        checkNullInput(phone, Phone.MESSAGE_CONSTRAINTS);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String qualification} into a {@code Qualification}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code qualification} is invalid.
     */
    public static Qualification parseQualification(String qualification) throws ParseException {
        checkNullInput(qualification, Qualification.MESSAGE_CONSTRAINTS);
        String trimmedQualification = qualification.trim();
        if (!Qualification.isValidQualification(trimmedQualification)) {
            throw new ParseException(Qualification.MESSAGE_CONSTRAINTS);
        }
        return new Qualification(trimmedQualification);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        checkNullInput(tag, Tag.MESSAGE_CONSTRAINTS);
        String trimmedTag = tag.trim();
        // Checks Tag argument against allowed Tags, throws exception if not valid
        if (!Tag.isValidTagName(trimmedTag) || !LevelSubjectCode.isValidTag(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_INVALID_TAG);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        checkNullInput(tags, Tag.MESSAGE_CONSTRAINTS);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Checks and throws a ParseException with a specified message if input is null.
     * @param input input to be checked
     * @param message the message to show with ParseException
     * @throws ParseException if input is null
     */
    private static void checkNullInput(Object input, String message) throws ParseException {
        if (input == null) {
            throw new ParseException(message);
        }
    }
}
