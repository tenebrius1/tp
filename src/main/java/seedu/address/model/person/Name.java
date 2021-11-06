package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should be between 1-50 "
            + "characters";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]{0,49}";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Formats the name by capitalizing the first character and removing unnecessary whitespaces.
     *
     * @return formatted name.
     */
    public String formatFullName() {
        String[] ls = fullName.split(" ");
        List<String> nameFragments = new ArrayList<>();
        for (String s : ls) {
            if (!s.equals("")) {
                nameFragments.add(s);
            }
        }
        StringBuilder formattedName = new StringBuilder("");
        for (String s : nameFragments) {
            // Capitalize first letter
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            formattedName.append(s).append(" ");
        }

        return formattedName.toString().trim();
    }

    @Override
    public String toString() {
        return formatFullName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equalsIgnoreCase(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
