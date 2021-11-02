package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUALIFICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
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

    // Valid Gender
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

        // Valid Name test
        Predicate<Person> tutorPredicate = predicate.and(namePredicateAlice);
        ChainedPredicate tutorTestPredicate =
                new ChainedPredicate.Builder().setName(alice).setPredicate(tutorPredicate).build();
        FilterCommand expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, " n/Alice", expectedFilterCommand);

        // Valid Gender test
        tutorPredicate = predicate.and(new GenderContainsGenderPredicate(List.of(female)));
        tutorTestPredicate =
                new ChainedPredicate.Builder().setGender(female).setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, GENDER_DESC_AMY, expectedFilterCommand);

        // Valid Qualification test
        tutorPredicate =
                predicate.and(new QualificationContainsQualificationPredicate(List.of(validQualification)));
        tutorTestPredicate = new ChainedPredicate.Builder().setQualification(validQualification)
                .setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, QUALIFICATION_DESC_BOB, expectedFilterCommand);
    }

    @Test
    public void parse_twoTutorFieldSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;

        // Valid Name and Gender test
        Predicate<Person> tutorPredicate = predicate.and(namePredicateAlice)
                .and(new GenderContainsGenderPredicate(List.of(female)));
        ChainedPredicate tutorTestPredicate =
                new ChainedPredicate.Builder().setName(alice).setGender(female).setPredicate(tutorPredicate).build();
        FilterCommand expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, NAME_DESC_ALICE + GENDER_DESC_AMY, expectedFilterCommand);

        // Valid Name and Qualification test
        tutorTestPredicate = new ChainedPredicate.Builder().setName(alice).setQualification(validQualification)
                .setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, NAME_DESC_ALICE + QUALIFICATION_DESC_BOB, expectedFilterCommand);

        // Valid Gender and Qualification test
        tutorTestPredicate = new ChainedPredicate.Builder().setGender(female).setQualification(validQualification)
                .setPredicate(tutorPredicate).build();
        expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, GENDER_DESC_AMY + QUALIFICATION_DESC_BOB, expectedFilterCommand);
    }

    @Test
    public void parse_threeTutorFieldSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;

        Predicate<Person> tutorPredicate = predicate;
        ChainedPredicate tutorTestPredicate =
                new ChainedPredicate.Builder().setName(alice).setGender(female).setQualification(validQualification)
                        .setPredicate(tutorPredicate).build();
        FilterCommand expectedFilterCommand = new FilterCommand(tutorTestPredicate);
        assertParseSuccess(parser, NAME_DESC_ALICE + GENDER_DESC_AMY + QUALIFICATION_DESC_BOB,
                expectedFilterCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Missing parameters
        assertParseFailure(parser, VALID_TUTOR_LETTER, MESSAGE_INVALID_FILTER_COMMAND_FORMAT);

        // Invalid qualification
        assertParseFailure(parser, INVALID_QUALIFICATION_DESC, Qualification.MESSAGE_CONSTRAINTS);

        // Invalid gender
        assertParseFailure(parser, INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // Blank gender
        assertParseFailure(parser, " " + PREFIX_GENDER, Gender.MESSAGE_CONSTRAINTS);

        // Blank qualification
        assertParseFailure(parser, " " + PREFIX_QUALIFICATION, Qualification.MESSAGE_CONSTRAINTS);
    }
}

