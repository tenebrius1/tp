package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.QualificationContainsQualificationPredicate;

public class FilterCommandParserTest {
    private static final String MESSAGE_INVALID_FILTER_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
    private final FilterCommandParser parser = new FilterCommandParser();

    // Valid Name
    private final Name alice = new Name("Alice");
    private final Name daniel = new Name("Daniel");

    // Valid Gender
    private final Gender male = new Gender("M");
    private final Gender female = new Gender("F");

    // Valid Qualification
    private final Qualification validQualification = new Qualification("0");

    // Predicates
    private final Predicate<Person> namePredicateDaniel = new NameContainsKeywordsPredicate(
            Collections.singletonList("Daniel"));
    private final Predicate<Person> namePredicateAlice = new NameContainsKeywordsPredicate(
            Collections.singletonList("Alice"));
    private final Predicate<Person> qualificationPredicate = new QualificationContainsQualificationPredicate(
            Collections.singletonList(validQualification));

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FILTER_COMMAND_FORMAT);
    }

    @Test
    public void parse_oneTutorFieldSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;
        Predicate<Person> tutorPredicate = predicate.and(namePredicateAlice);
        ChainedPredicate tutorTestPredicate =
                new ChainedPredicate.Builder().setName(alice).setPredicate(tutorPredicate).build();
        FilterCommand expectedFilterCommand = new FilterCommand(tutorTestPredicate);

        // Valid Name test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + " n/alice", expectedFilterCommand);

        tutorPredicate = predicate.and(new GenderContainsGenderPredicate(List.of(female)));
        tutorTestPredicate =
                new ChainedPredicate.Builder().setGender(female).setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);

        // Valid Gender test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + GENDER_DESC_AMY, expectedFilterCommand);

        tutorPredicate =
                predicate.and(new QualificationContainsQualificationPredicate(List.of(validQualification)));
        tutorTestPredicate = new ChainedPredicate.Builder().setQualification(validQualification)
                .setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);

        // Valid Qualification test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + QUALIFICATION_DESC_BOB, expectedFilterCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Name
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}

