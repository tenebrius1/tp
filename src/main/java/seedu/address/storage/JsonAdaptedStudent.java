package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

public class JsonAdaptedStudent extends JsonAdaptedPerson {
    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("gender") String gender,
                              @JsonProperty("remark") String remark,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tag) {
        super(name, phone, gender, remark, tag);
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        super(source.getName().fullName, source.getPhone().value,
                source.getGender().genderSymbol, source.getRemark().description, getAddedTags(source));
    }

    /**
     * Gets a List of JsonAdaptedTags based on tags from a given Student.
     *
     * @param source A Student to add tags from.
     * @return A List of JsonAdaptedTags from the source.
     */
    private static List<JsonAdaptedTag> getAddedTags(Student source) {
        List<JsonAdaptedTag> tagged = new ArrayList<>();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        return tagged;
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : super.getTags()) {
            studentTags.add(tag.toModelType());
        }

        if (super.getName() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(super.getName())) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(super.getName());

        if (super.getPhone() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(super.getPhone())) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(super.getPhone());

        if (super.getGender() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(super.getGender())) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(super.getGender());

        if (super.getRemark() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(super.getRemark())) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(super.getRemark());

        final Set<Tag> modelTags = new HashSet<>(studentTags);
        return new Student(modelName, modelPhone, modelGender, modelRemark, modelTags);
    }
}
