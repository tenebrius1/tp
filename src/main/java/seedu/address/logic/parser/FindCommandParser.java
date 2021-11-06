package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.QualificationContainsQualificationPredicate;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Removes any trailing and leading white spaces
        String trimmedArgs = args.trim();

        // If user just type in "find", then throw ParseException
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        PersonType personType;
        try {
            personType = ParserUtil.parsePersonType(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), pe);
        }

        return new FindCommand(generatePredicate(trimmedArgs, personType), personType);
    }

    /**
     * Generates the predicate to be used in FindCommand.
     *
     * @return The Predicate used to filter the student/tutor list
     */
    private Predicate<Person> generatePredicate(String args, PersonType personType) throws ParseException {
        Predicate<Person> predicate = x -> true;
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
        int prefixCount = 0;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG,
                PREFIX_QUALIFICATION, PREFIX_GENDER);

        String trimmed = argMultimap.getPreamble().trim();

        if (trimmed.length() > 1) { // Checks if user entered anything other than t/s in preamble
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            prefixCount += 1;
            predicate = handleName(predicate, builder, argMultimap);
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            prefixCount += 1;
            predicate = handleGender(predicate, builder, argMultimap);
        }
        if (argMultimap.getValue(PREFIX_QUALIFICATION).isPresent()) {
            prefixCount += 1;
            predicate = handleQualification(personType, predicate, builder, argMultimap);
        }
        if (parseTags(argMultimap.getAllValues(PREFIX_TAG)).isPresent()) {
            prefixCount += 1;
            predicate = handleTags(predicate, builder, argMultimap);
        }
        if (prefixCount == 0) { // Checks if user entered any parameter
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return builder.setPredicate(predicate).build();
    }

    private Predicate<Person> handleTags(Predicate<Person> predicate, ChainedPredicate.Builder builder,
                                         ArgumentMultimap argMultimap) throws ParseException {
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Tag> parsedTags = new ArrayList<>(tags);
        predicate = predicate.and(new TagsContainTagPredicate(parsedTags));
        builder.setTags(parsedTags);
        return predicate;
    }

    private Predicate<Person> handleQualification(PersonType personType, Predicate<Person> predicate,
                                                  ChainedPredicate.Builder builder,
                                                  ArgumentMultimap argMultimap) throws ParseException {
        // Guard clause in case user searches student using qualification
        if (personType == PersonType.STUDENT) {
            throw new ParseException(MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION);
        }

        Qualification qualification =
                ParserUtil.parseQualification(argMultimap.getValue(PREFIX_QUALIFICATION).get());
        Qualification[] qualificationList = new Qualification[] {qualification};
        predicate =
                predicate.and(new QualificationContainsQualificationPredicate(Arrays.asList(qualificationList)));
        builder.setQualification(qualification);
        return predicate;
    }

    private Predicate<Person> handleGender(Predicate<Person> predicate, ChainedPredicate.Builder builder,
                                           ArgumentMultimap argMultimap) throws ParseException {
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Gender[] genderList = new Gender[] {gender};
        predicate = predicate.and(new GenderContainsGenderPredicate(Arrays.asList(genderList)));
        builder.setGender(gender);
        return predicate;
    }

    private Predicate<Person> handleName(Predicate<Person> predicate, ChainedPredicate.Builder builder,
                                         ArgumentMultimap argMultimap) throws ParseException {
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String[] nameList = new String[] {name.toString()};
        predicate = predicate.and(new NameContainsKeywordsPredicate(Arrays.asList(nameList)));
        builder.setName(name);
        return predicate;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<List<Tag>> parseTags(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") // check for empty tags
                ? Collections.emptySet() // return empty set if there are no tags
                : tags; // else return the tags
        return Optional.of(List.copyOf(ParserUtil.parseTags(tagSet))); // convert set to List<Tags>
    }
}
