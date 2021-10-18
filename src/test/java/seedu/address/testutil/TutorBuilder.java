package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutor objects.
 */
public class TutorBuilder {
    public static final String DEFAULT_GENDER = "M";
    public static final String DEFAULT_NAME = "Bob Choo";
    public static final String DEFAULT_PHONE = "22222222";
    public static final String DEFAULT_QUALIFICATION = "0";
    public static final String DEFAULT_TAG = "PM";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Qualification qualification;
    private Set<Tag> tags;

    /**
     * Creates a {@code TutorBuilder} with the default details.
     */
    public TutorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        qualification = new Qualification(DEFAULT_QUALIFICATION);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
    }

    /**
     * Initializes the TutorBuilder with the data of {@code tutorToCopy}.
     */
    public TutorBuilder(Tutor tutorToCopy) {
        name = tutorToCopy.getName();
        phone = tutorToCopy.getPhone();
        gender = tutorToCopy.getGender();
        qualification = tutorToCopy.getQualification();
        tags = new HashSet<>(tutorToCopy.getTags());
    }

    /**
     * Sets the {@code Gender} of the {@code Tutor} that we are building.
     */
    public TutorBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Tutor} that we are building.
     */
    public TutorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Tutor} that we are building.
     */
    public TutorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tutor} that we are building.
     */
    public TutorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Qualification} of the {@code Tutor} that we are building.
     */
    public TutorBuilder withQualification(String qualification) {
        this.qualification = new Qualification(qualification);
        return this;
    }

    public Tutor build() {
        return new Tutor(name, phone, gender, qualification, tags);
    }
}
