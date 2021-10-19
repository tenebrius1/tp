package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in CLITutors.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {
    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Gender gender, Set<Tag> tags) {
        super(name, phone, gender, tags);
        requireAllNonNull(name, phone, gender, tags);
    }

    @Override
    public Qualification getQualification() {
        return null;
    }
}
