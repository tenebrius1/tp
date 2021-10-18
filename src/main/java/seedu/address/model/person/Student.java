package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in CLITutors.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {
    public static final String MESSAGE_TOO_MANY_TAGS = "Students can only have 1 tag.";

    private final Tag tag;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Gender gender, Tag tag) {
        super(name, phone, gender, wrapTag(tag));
        requireAllNonNull(name, phone, gender, tag);
        this.tag = tag;
    }

    /**
     * Wraps given tag in a Set.
     *
     * @param tag The given tag to wrap.
     * @return A Set containing only the given tag.
     */
    private static Set<Tag> wrapTag(Tag tag) {
        Set<Tag> set = new HashSet<>();
        set.add(tag);
        return set;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public Qualification getQualification() {
        return null;
    }
}
