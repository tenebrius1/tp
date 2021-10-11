package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM_TP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutor expectedTutor = new TutorBuilder(BOB).withTags(VALID_TAG_TP).build();
        Student expectedStudent = new StudentBuilder(AMY).withTag(VALID_TAG_PM).build();

        //Tests for tutors
        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, VALID_TUTOR_TYPE));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, VALID_TUTOR_TYPE));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_AMY
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, VALID_TUTOR_TYPE));

        // TODO: Allow for case-insensitive input
//        // Check that tags are case-insensitive
//        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
//                + QUALIFICATION_DESC_BOB + TAG_DESC_UNCAPITALIZED, new AddCommand(expectedTutor, VALID_TUTOR_TYPE));

        // multiple tag prefixes - tags in latest tag prefix accepted
        Person expectedTutorMultipleTagPrefixes = new TutorBuilder(BOB).withTags(VALID_TAG_TP).build();
        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM + TAG_DESC_TP,
                new AddCommand(expectedTutorMultipleTagPrefixes, VALID_TUTOR_TYPE));

        // multiple tag arguments - all arguments for tag prefix accepted
        Person expectedTutorMultipleTags = new TutorBuilder(BOB).withTags(VALID_TAG_TP, VALID_TAG_PM)
                .build();
        assertParseSuccess(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP, new AddCommand(expectedTutorMultipleTags, VALID_TUTOR_TYPE));

        //Tests for students
        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_STUDENT_LETTER + NAME_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_PM, new AddCommand(expectedStudent, VALID_STUDENT_TYPE));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, VALID_STUDENT_LETTER + NAME_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_PM, new AddCommand(expectedStudent, VALID_STUDENT_TYPE));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, VALID_STUDENT_LETTER + NAME_DESC_AMY + PHONE_DESC_AMY + GENDER_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_PM, new AddCommand(expectedStudent, VALID_STUDENT_TYPE));

        // one tag - accepted
        Student expectedStudentOneTag = new StudentBuilder(AMY).withTag(VALID_TAG_TP).build();
        assertParseSuccess(parser, VALID_STUDENT_LETTER + NAME_DESC_AMY + PHONE_DESC_AMY + GENDER_DESC_AMY
                + TAG_DESC_TP, new AddCommand(expectedStudentOneTag, VALID_STUDENT_TYPE));

        // multiple tag prefixes - tags in latest tag prefix accepted
        Student expectedStudentMultipleTagPrefix = new StudentBuilder(AMY).withTag(VALID_TAG_TP).build();
        assertParseSuccess(parser, VALID_STUDENT_LETTER + NAME_DESC_AMY + PHONE_DESC_AMY + GENDER_DESC_AMY
                + TAG_DESC_PM + TAG_DESC_TP, new AddCommand(expectedStudentMultipleTagPrefix, VALID_STUDENT_TYPE));
    }

    @Test
    public void parse_studentTooManyTags_failure() {
        // multiple tags - not accepted
        String expectedMultipleTagsStudentMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_TOO_MANY_TAGS);

        // Too many tag arguments - fail
        assertParseFailure(parser, VALID_STUDENT_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_PM_TP, expectedMultipleTagsStudentMessage);

        // Multiple tag prefixes, latest one with too many tags argument taken - fail
        assertParseFailure(parser, VALID_STUDENT_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_PM + TAG_DESC_PM_TP, expectedMultipleTagsStudentMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + VALID_NAME_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + VALID_PHONE_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_GENDER_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing qualification prefix
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + VALID_QUALIFICATION_BOB + TAG_DESC_PM, expectedMessage);

        // missing tag prefix
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + VALID_TAG_PM, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_GENDER_BOB
                + VALID_QUALIFICATION_BOB + VALID_TAG_PM, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_NAME_DESC + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + INVALID_PHONE_DESC + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_GENDER_DESC
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP, Gender.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VALID_TUTOR_LETTER + INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_GENDER_DESC
                        + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP,
                Name.MESSAGE_CONSTRAINTS);

        // Invalid personType (not "t" or "s")
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INVALID_PREAMBLE + VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP, expectedMessage);
    }
}
