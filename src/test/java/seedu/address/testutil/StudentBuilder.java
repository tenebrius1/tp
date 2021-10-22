package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "11111111";
    public static final String DEFAULT_TAG = "PM";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Set<Tag> tags;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
    }

    /**
     * Initializes the StudentBuilder with the data of {@code personToCopy}.
     */
    public StudentBuilder(Student personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        gender = personToCopy.getGender();
        tags = personToCopy.getTags();
    }

    /**
     * Sets the {@code Gender} of the {@code Student} that we are building.
     */
    public StudentBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Tag} of the {@code Student} that we are building.
     */
    public StudentBuilder withTag(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Student build() {
        return new Student(name, phone, gender, tags);
    }
}
