package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Tutor in CLITutors.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutor extends Person {
    private final Qualification qualification;

    /**
     * Every field must be present and not null.
     */
    public Tutor(Name name, Phone phone, Gender gender, Qualification qualification, Set<Tag> tags) {
        super(name, phone, gender, tags);
        requireAllNonNull(name, phone, gender, tags);
        this.qualification = qualification;
    }

    public Qualification getQualification() {
        return qualification;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutor)) {
            return false;
        }

        Tutor otherTutor = (Tutor) other;
        return otherTutor.getName().equals(getName())
                && otherTutor.getPhone().equals(getPhone())
                && otherTutor.getGender().equals(getGender())
                && otherTutor.getQualification().equals(getQualification())
                && otherTutor.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.getName(), super.getPhone(), super.getGender(), qualification, super.getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(super.getName())
                .append(";\nPhone: ")
                .append(super.getPhone())
                .append(";\nGender: ")
                .append(super.getGender())
                .append(";\nQualification: ")
                .append(getQualification());

        Set<Tag> tags = super.getTags();
        if (!tags.isEmpty()) {
            builder.append(";\nTags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
