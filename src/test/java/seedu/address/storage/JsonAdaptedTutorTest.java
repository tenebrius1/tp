package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.LevelSubjectCode;
import seedu.address.model.tag.Tag;

public class JsonAdaptedTutorTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GENDER = "Z";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_QUALIFICATION = "4";
    private static final String INVALID_TAG = "ABCDE";
    private static final String INVALID_TAG_NON_ALPHANUMERIC = "T+";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_QUALIFICATION = BENSON.getQualification().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTutorDetails_returnsTutor() throws Exception {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(BENSON);
        assertEquals(BENSON, tutor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(INVALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(null, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        System.out.println(LevelSubjectCode.getLabel("SM"));
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, INVALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, null, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, INVALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, null,
                VALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_nullQualification_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                null, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Qualification.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidQualification_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                INVALID_QUALIFICATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Qualification.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, INVALID_REMARK, VALID_TAGS);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidTagsNonAlphaNumeric_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG_NON_ALPHANUMERIC));
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, invalidTags);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutor::toModelType);
    }

    @Test
    public void toModelType_invalidTagName_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTutor tutor = new JsonAdaptedTutor(VALID_NAME, VALID_PHONE, VALID_GENDER,
                VALID_QUALIFICATION, VALID_REMARK, invalidTags);
        assertThrows(IllegalValueException.class, tutor::toModelType);
    }
}
