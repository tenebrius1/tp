package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
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
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Removes any trailing and leading white spaces
        String trimmedArgs = args.trim();

        // If user just type in "filter", then throw ParseException
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(generatePredicate(trimmedArgs));
    }

    /**
     * Generates the predicate to be used in FilterCommand.
     *
     * @return The Predicate used to filter the match list
     */
    private Predicate<Person> generatePredicate(String args) throws ParseException {
        Predicate<Person> predicate = x -> true;
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG,
                PREFIX_QUALIFICATION, PREFIX_GENDER);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicate = handleName(predicate, builder, argMultimap);
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            predicate = handleGender(predicate, builder, argMultimap);
        }
        if (argMultimap.getValue(PREFIX_QUALIFICATION).isPresent()) {
            predicate = handleQualification(predicate, builder, argMultimap);
        }
        if (parseTags(argMultimap.getAllValues(PREFIX_TAG)).isPresent()) {
            predicate = handleTags(predicate, builder, argMultimap);
        }

        return builder.setPredicate(predicate).build();
    }

    private Predicate<Person> handleTags(Predicate<Person> predicate, ChainedPredicate.Builder builder,
                                         ArgumentMultimap argMultimap) throws ParseException {
        List<Tag> tags = parseTags(argMultimap.getAllValues(PREFIX_TAG)).get();
        predicate = predicate.and(new TagsContainTagPredicate(tags));
        builder.setTags(tags);
        return predicate;
    }

    private Predicate<Person> handleQualification(Predicate<Person> predicate, ChainedPredicate.Builder builder,
                                                  ArgumentMultimap argMultimap) throws ParseException {
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
