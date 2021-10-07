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
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

public class JsonAdaptedTutor extends JsonAdaptedPerson {

    private final String qualification;

    /**
     * Constructs a {@code JsonAdaptedTutor} with the given tutor details.
     */
    @JsonCreator
    public JsonAdaptedTutor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("gender") String gender, @JsonProperty("qualification") String qualification,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        super(name, phone, gender, tagged);
        this.qualification = qualification;
    }

    /**
     * Converts a given {@code Tutor} into this class for Jackson use.
     */
    public JsonAdaptedTutor(Tutor source) {
        super(source.getName().fullName, source.getPhone().value,
                source.getGender().genderSymbol, getAddedTags(source));
        qualification = source.getQualification().index;
    }

    /**
     * Gets a List of JsonAdaptedTags based on tags from a given Tutor.
     *
     * @param source A Tutor to add tags from.
     * @return A List of JsonAdaptedTags from the source.
     */
    private static List<JsonAdaptedTag> getAddedTags(Tutor source) {
        List<JsonAdaptedTag> tagged = new ArrayList<>();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        return tagged;
    }

    /**
     * Converts this Jackson-friendly adapted tutor object into the model's {@code Tutor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutor.
     */
    public Tutor toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : super.getTags()) {
            personTags.add(tag.toModelType());
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

        if (qualification == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Qualification.class.getSimpleName()));
        }
        if (!Qualification.isValidQualification(qualification)) {
            throw new IllegalValueException(Qualification.MESSAGE_CONSTRAINTS);
        }
        final Qualification modelQualification = new Qualification(qualification);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Tutor(modelName, modelPhone, modelGender, modelQualification, modelTags);
    }
}
