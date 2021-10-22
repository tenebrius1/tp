package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {
    public static final String MESSAGE_CONSTRAINTS = "Gender should be 'M' or 'F'";
    public static final String VALIDATION_REGEX = "[MmFf]";
    public final String genderSymbol;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        genderSymbol = gender.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return genderSymbol;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && genderSymbol.equalsIgnoreCase(((Gender) other).genderSymbol)); // state check
    }

    @Override
    public int hashCode() {
        return genderSymbol.toUpperCase().hashCode();
    }
}
