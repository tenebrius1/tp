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
    public Student(Name name, Phone phone, Gender gender, Remark remark, Set<Tag> tags) {
        super(name, phone, gender, remark, tags);
        requireAllNonNull(name, phone, gender, tags);
    }
}
