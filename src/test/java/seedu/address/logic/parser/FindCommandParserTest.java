package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUALIFICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
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

public class FindCommandParserTest {
    private static final String MESSAGE_INVALID_FIND_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
    private final FindCommandParser parser = new FindCommandParser();

    // Valid Name
    private final Name alice = new Name("Alice");
    private final Name daniel = new Name("Daniel");

    // Valid Gender
    private final Gender male = new Gender("M");
    private final Gender female = new Gender("F");

    // Valid Qualification
    private final Qualification validQualification = new Qualification("0");

    // Valid Tags
    private final Tag tag = new Tag("PM");

    // Predicates
    private final Predicate<Person> namePredicateDaniel = new NameContainsKeywordsPredicate(
            Collections.singletonList("Daniel"));
    private final Predicate<Person> namePredicateAlice = new NameContainsKeywordsPredicate(
            Collections.singletonList("Alice"));
    private final Predicate<Person> qualificationPredicate = new QualificationContainsQualificationPredicate(
            Collections.singletonList(validQualification));

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", MESSAGE_INVALID_FIND_COMMAND_FORMAT);
    }

    @Test
    public void parse_oneTutorFieldSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;
        Predicate<Person> tutorPredicate = predicate.and(namePredicateAlice);
        ChainedPredicate tutorTestPredicate =
                new ChainedPredicate.Builder().setName(alice).setPredicate(tutorPredicate).build();
        FindCommand expectedTutorFindCommand = new FindCommand(tutorTestPredicate, PersonType.TUTOR);

        // Valid Name test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + " n/alice", expectedTutorFindCommand);

        tutorPredicate = predicate.and(new GenderContainsGenderPredicate(List.of(female)));
        tutorTestPredicate =
                new ChainedPredicate.Builder().setGender(female).setPredicate(tutorPredicate).build();
        expectedTutorFindCommand = new FindCommand(tutorTestPredicate, PersonType.TUTOR);

        // Valid Gender test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + GENDER_DESC_AMY, expectedTutorFindCommand);

        tutorPredicate = predicate.and(new TagsContainTagPredicate(List.of(tag)));
        tutorTestPredicate = new ChainedPredicate.Builder().setTags(List.of(tag))
                        .setPredicate(tutorPredicate).build();
        expectedTutorFindCommand = new FindCommand(tutorTestPredicate, PersonType.TUTOR);

        // Valid Tag test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + TAG_DESC_PM, expectedTutorFindCommand);

        tutorPredicate =
                predicate.and(new QualificationContainsQualificationPredicate(List.of(validQualification)));
        tutorTestPredicate = new ChainedPredicate.Builder().setQualification(validQualification)
                        .setPredicate(tutorPredicate).build();
        expectedTutorFindCommand = new FindCommand(tutorTestPredicate, PersonType.TUTOR);

        // Valid Qualification test
        assertParseSuccess(parser, VALID_TUTOR_LETTER + QUALIFICATION_DESC_BOB, expectedTutorFindCommand);
    }

    @Test
    public void parse_oneStudentFieldSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;
        Predicate<Person> studentPredicate =
                predicate.and(namePredicateDaniel);
        ChainedPredicate studentTestPredicate =
                new ChainedPredicate.Builder().setName(daniel).setPredicate(studentPredicate).build();
        FindCommand expectedStudentFindCommand =
                new FindCommand(studentTestPredicate, PersonType.STUDENT);

        // Valid Name test
        assertParseSuccess(parser, VALID_STUDENT_LETTER + " n/Daniel", expectedStudentFindCommand);

        studentPredicate = predicate.and(new GenderContainsGenderPredicate(List.of(male)));
        studentTestPredicate =
                new ChainedPredicate.Builder().setGender(male).setPredicate(studentPredicate).build();
        expectedStudentFindCommand = new FindCommand(studentTestPredicate, PersonType.STUDENT);

        // Valid Gender test
        assertParseSuccess(parser, VALID_STUDENT_LETTER + GENDER_DESC_BOB, expectedStudentFindCommand);

        studentPredicate = predicate.and(new TagsContainTagPredicate(List.of(tag)));
        studentTestPredicate =
                new ChainedPredicate.Builder().setTags(List.of(tag))
                        .setPredicate(studentPredicate).build();
        expectedStudentFindCommand = new FindCommand(studentTestPredicate, PersonType.STUDENT);

        // Valid Tag test
        assertParseSuccess(parser, VALID_STUDENT_LETTER + TAG_DESC_PM, expectedStudentFindCommand);
    }

    @Test
    public void parse_someTutorFieldsSpecified_success() {
        // Setup
        Predicate<Person> predicate = x -> true;
        predicate = predicate.and(namePredicateAlice);
        predicate = predicate.and(qualificationPredicate);
        ChainedPredicate chainedPredicate =
                new ChainedPredicate.Builder().setName(alice)
                        .setQualification(validQualification).setPredicate(predicate).build();
        FindCommand expectedTutorFindCommand = new FindCommand(chainedPredicate, PersonType.TUTOR);

        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_ALICE + QUALIFICATION_DESC_BOB,
                expectedTutorFindCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Name Test
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Invalid Tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG);

        // Invalid Tag argument ahead of valid Tag
        assertParseFailure(parser, VALID_TUTOR_LETTER
                + INVALID_TAG_DESC + " " + VALID_TAG_TP, Tag.MESSAGE_INVALID_TAG);

        assertParseFailure(parser, VALID_TUTOR_LETTER
                + TAG_DESC_PM + " " + INVALID_TAG , Tag.MESSAGE_INVALID_TAG);

        // Multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_NAME_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // Invalid preamble
        assertParseFailure(parser, INVALID_PREAMBLE, MESSAGE_INVALID_FIND_COMMAND_FORMAT);

        // Multiple preamble
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + VALID_STUDENT_LETTER,
                MESSAGE_INVALID_FIND_COMMAND_FORMAT);

        // Parameters absent
        assertParseFailure(parser, VALID_TUTOR_LETTER, MESSAGE_INVALID_FIND_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid preamble
        assertParseFailure(parser, INVALID_PREAMBLE, MESSAGE_INVALID_FIND_COMMAND_FORMAT);

        // Invalid Name
        assertParseFailure(parser, VALID_STUDENT_LETTER + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Blank Name
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);

        // Invalid tag
        assertParseFailure(parser, VALID_STUDENT_LETTER + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG);

        // Blank tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + PREFIX_TAG, Tag.MESSAGE_INVALID_TAG);

        // Invalid gender
        assertParseFailure(parser, VALID_STUDENT_LETTER + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // Blank gender
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + PREFIX_GENDER, Gender.MESSAGE_CONSTRAINTS);

        // Invalid qualification
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_QUALIFICATION_DESC, Qualification.MESSAGE_CONSTRAINTS);

        // Blank qualification
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + PREFIX_QUALIFICATION, Qualification.MESSAGE_CONSTRAINTS);

        // Find Qualification for Student
        assertParseFailure(parser, VALID_STUDENT_LETTER + QUALIFICATION_DESC_BOB,
                MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION);
    }
}
