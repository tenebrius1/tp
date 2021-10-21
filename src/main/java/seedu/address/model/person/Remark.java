package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS =
            "Remarks should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".*\\S.*";

    public final String description;

    /**
     * Constructs a {@code Name}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        description = remark;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && description.equalsIgnoreCase(((Remark) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
