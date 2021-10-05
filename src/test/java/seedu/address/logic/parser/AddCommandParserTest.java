package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.*;

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
        String personTypeTutor = "t";
        Student expectedStudent = new StudentBuilder(AMY).withTag(VALID_TAG_PM).build();
        String personTypeStudent = "s";

        //Tests for tutors
        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_TUTOR_TYPE + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, personTypeTutor));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, personTypeTutor));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_AMY
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedTutor, personTypeTutor));

        // multiple tags - all accepted
        Person expectedTutorMultipleTags = new TutorBuilder(BOB).withTags(VALID_TAG_TP, VALID_TAG_PM)
                .build();
        assertParseSuccess(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_PM + TAG_DESC_TP, new AddCommand(expectedTutorMultipleTags, personTypeTutor));

        //Tests for students
        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_STUDENT_TYPE + NAME_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_TP, new AddCommand(expectedStudent, personTypeStudent));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, VALID_STUDENT_TYPE + NAME_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_TP, new AddCommand(expectedStudent, personTypeStudent));

        // multiple genders - last gender accepted
        assertParseSuccess(parser,  VALID_STUDENT_TYPE + NAME_DESC_AMY + PHONE_DESC_AMY + GENDER_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_TP, new AddCommand(expectedStudent, personTypeStudent));

        // one tag - accepted
        Student expectedStudentOneTag = new StudentBuilder(AMY).withTag(VALID_TAG_TP)
                .build();
        assertParseSuccess(parser, VALID_STUDENT_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_TP, new AddCommand(expectedStudentOneTag, personTypeStudent));
    }

    @Test
    public void parse_studentTooManyTags_failure() {
        // multiple tags - not accepted
        String expectedMultipleTagsStudentMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_TOO_MANY_TAGS);

        assertParseFailure(parser, VALID_STUDENT_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_PM + TAG_DESC_TP, expectedMultipleTagsStudentMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TUTOR_TYPE + VALID_NAME_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + VALID_PHONE_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_GENDER_BOB
                + QUALIFICATION_DESC_BOB + TAG_DESC_PM, expectedMessage);

        // missing qualification prefix
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + VALID_QUALIFICATION_BOB + TAG_DESC_PM, expectedMessage);

        // missing tag prefix
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + QUALIFICATION_DESC_BOB + VALID_TAG_PM, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TUTOR_TYPE + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_GENDER_BOB
                + VALID_QUALIFICATION_BOB + VALID_TAG_PM, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_TUTOR_TYPE + INVALID_NAME_DESC + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_PM + TAG_DESC_TP, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + INVALID_PHONE_DESC + GENDER_DESC_BOB
                + TAG_DESC_PM + TAG_DESC_TP, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_GENDER_DESC
                + TAG_DESC_PM + TAG_DESC_TP, Gender.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_TP, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VALID_TUTOR_TYPE + INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_GENDER_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_TUTOR_TYPE + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_PM + TAG_DESC_TP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
