package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEGER_MAX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEGER_MIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUALIFICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ZERO_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM_TP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.EditCommand.EditTutorDescriptor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EditTutorDescriptorBuilder;

public class EditCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, VALID_TUTOR_LETTER + "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INDEX + NAME_DESC_AMY,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INDEX + NAME_DESC_AMY,
                MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_ZERO_INDEX + NAME_DESC_AMY,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_ZERO_INDEX + NAME_DESC_AMY,
                MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);

        // index greater than MAX_INT
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INTEGER_MAX + NAME_DESC_AMY,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INTEGER_MAX + NAME_DESC_AMY,
                MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);

        // index less than MIN_INT
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + INVALID_INTEGER_MIN + NAME_DESC_AMY,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + INVALID_INTEGER_MIN + NAME_DESC_AMY,
                MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);

        // invalid arguments being parsed as preamble
        Index targetIndex = INDEX_SECOND_PERSON;
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + targetIndex.getOneBased()
                + " some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_TUTOR_LETTER + " " + targetIndex.getOneBased()
                + " i/string", MESSAGE_INVALID_FORMAT);

        // invalid PersonType
        assertParseFailure(parser, INVALID_PREAMBLE + " " + INDEX_FIRST_PERSON + NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTutorValue_failure() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String validIndex = " " + targetIndex.getOneBased();

        // invalid name
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
            + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
                + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
                + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS);

        // invalid qualification
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
                + INVALID_QUALIFICATION_DESC, Qualification.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
            + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG);

        // invalid tag argument ahead of valid tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
                + INVALID_TAG_DESC + " " + VALID_TAG_TP, Tag.MESSAGE_INVALID_TAG);

        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex
                + TAG_DESC_PM + " " + INVALID_TAG , Tag.MESSAGE_INVALID_TAG);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex + PHONE_DESC_BOB
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_TUTOR_LETTER + validIndex + INVALID_NAME_DESC + INVALID_PHONE_DESC,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidStudentValue_failure() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String validIndex = " " + targetIndex.getOneBased();

        // invalid name
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS);

        // qualification incorrectly present in student
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + QUALIFICATION_DESC_BOB, MESSAGE_INVALID_INPUT_STUDENT_WITH_QUALIFICATION);

        // invalid tag
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG);

        // invalid tag argument ahead of valid tag
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + INVALID_TAG_DESC + " " + VALID_TAG_TP, Tag.MESSAGE_INVALID_TAG);

        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex
                + TAG_DESC_PM + " " + INVALID_TAG , Tag.MESSAGE_INVALID_TAG);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex + PHONE_DESC_BOB
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_STUDENT_LETTER + validIndex + INVALID_NAME_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allTutorFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_PM_TP;

        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB)
            .withQualification(VALID_QUALIFICATION_BOB)
            .withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_PM, VALID_TAG_TP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allStudentFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + REMARK_DESC_AMY + TAG_DESC_TP;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withGender(VALID_GENDER_AMY)
                .withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_TP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someTutorFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withPhone(VALID_PHONE_BOB)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someStudentFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneTutorFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + NAME_DESC_BOB;
        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_BOB;
        descriptor = new EditTutorDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + GENDER_DESC_BOB;
        descriptor = new EditTutorDescriptorBuilder().withGender(VALID_GENDER_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // qualification
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + QUALIFICATION_DESC_BOB;
        descriptor = new EditTutorDescriptorBuilder().withQualification(VALID_QUALIFICATION_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + REMARK_DESC_BOB;
        descriptor = new EditTutorDescriptorBuilder().withRemark(VALID_REMARK_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + TAG_DESC_TP;
        descriptor = new EditTutorDescriptorBuilder().withTags(VALID_TAG_TP).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneStudentFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + REMARK_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + TAG_DESC_TP;
        descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_TP).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_AMY
            + TAG_DESC_TP + PHONE_DESC_AMY + TAG_DESC_TP
            + PHONE_DESC_BOB + TAG_DESC_PM;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_PM)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + INVALID_TAG_DESC + TAG_DESC_PM;
        descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_PM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + INVALID_TAG_DESC + TAG_DESC_PM_TP;
        descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_PM, VALID_TAG_TP).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.STUDENT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_failure() {
        String userInput = VALID_TUTOR_LETTER + " " + INDEX_THIRD_PERSON.getOneBased() + TAG_EMPTY;
        assertParseFailure(parser, userInput, Tag.MESSAGE_INVALID_TAG);
    }
}
