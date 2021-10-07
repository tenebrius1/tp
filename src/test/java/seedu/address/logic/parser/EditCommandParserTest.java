package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTOR_LETTER;
import static seedu.address.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.PersonType.MESSAGE_INVALID_PERSON_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.EditCommand.EditTutorDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EditTutorDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_STUDENT_LETTER + " " + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_STUDENT_LETTER + " 1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, VALID_TUTOR_LETTER + "", MESSAGE_INVALID_FORMAT);

        // no student or tutor letter specified, since the very first thing we check for Edit command is the
        // type of person, Error message should correspond to error of invalid personType
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_PERSON_TYPE);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, VALID_STUDENT_LETTER + " -5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, VALID_STUDENT_LETTER + " 0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1"
            + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_STUDENT_LETTER + " 1"
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1"
            + INVALID_TAG_DESC, Tag.MESSAGE_INVALID_TAG); // invalid tag
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1"
                + INVALID_TAG_DESC + " " + VALID_TAG_TP, Tag.MESSAGE_INVALID_TAG); // invalid tag argument ahead of valid tag

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_STUDENT_LETTER + " 1" + PHONE_DESC_BOB
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_TUTOR_LETTER + " 1" + INVALID_NAME_DESC + INVALID_PHONE_DESC,
            Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allTutorFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP;

        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB)
            .withQualification(VALID_QUALIFICATION_BOB)
            .withTags(VALID_TAG_PM, VALID_TAG_TP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allStudentFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_AMY + GENDER_DESC_AMY
                + NAME_DESC_AMY + TAG_DESC_TP;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withGender(VALID_GENDER_AMY)
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
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditTutorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditTutorDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);
        assertParseSuccess(parser, userInput, expectedCommand);

        // qualification
        userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + QUALIFICATION_DESC_BOB;
        descriptor = new EditTutorDescriptorBuilder().withQualification(VALID_QUALIFICATION_BOB).build();
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
        userInput = VALID_STUDENT_LETTER + " " + targetIndex.getOneBased() + GENDER_DESC_BOB;
        descriptor = new EditStudentDescriptorBuilder().withGender(VALID_GENDER_BOB).build();
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
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = VALID_TUTOR_LETTER + " " + targetIndex.getOneBased() + TAG_EMPTY;

        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor, PersonType.TUTOR);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
