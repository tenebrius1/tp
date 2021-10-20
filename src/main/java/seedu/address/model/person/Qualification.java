package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutor's qualification in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQualification(String)}
 */
public class Qualification {
    public static final String MESSAGE_CONSTRAINTS = "Qualification should be '0', '1', '2' or '3'";
    public static final String VALIDATION_REGEX = "[0123]";
    public final String index;

    enum Qualifications {
        PREUNI("0", "Pre-University"),
        UNI("1", "University"),
        POSTGRAD("2", "Post-Graduate"),
        MOE("3", "MOE-Trained"),
        INVALID("Invalid", "Invalid");

        public final String qualificationCode;
        public final String label;

        Qualifications(String qualificationCode, String label) {
            this.qualificationCode = qualificationCode;
            this.label = label;
        }

        /**
         * Returns label that corresponds to the qualificationCode user has given.
         *
         * @param qualificationCode qualificationCode from user input.
         * @return Label corresponding to the qualificationCode.
         */
        public static String getLabel(String qualificationCode) {
            for (Qualifications x : values()) {
                if (x.qualificationCode.equalsIgnoreCase(qualificationCode)) {
                    return x.label;
                }
            }
            return INVALID.label;
        }
    }

    /**
     * Constructs a {@code Qualification}.
     *
     * @param qualification A valid qualification.
     */
    public Qualification(String qualification) {
        requireNonNull(qualification);
        checkArgument(isValidQualification(qualification), MESSAGE_CONSTRAINTS);
        this.index = qualification;
    }

    /**
     * Returns true if a given string is a valid qualification.
     */
    public static boolean isValidQualification(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Qualification // instanceof handles nulls
                && index.equals(((Qualification) other).index)); // state check
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
