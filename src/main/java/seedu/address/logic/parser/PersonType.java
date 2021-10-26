package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Type of person specified by command.
 */
public enum PersonType {
    STUDENT,
    TUTOR;

    public static final String MESSAGE_INVALID_PERSON_TYPE = "The given person type is not t or s.";

    /**
     * Returns the appropriate PersonType entered by the user.
     * If the personType is of a wrong format a ParseException is thrown.
     *
     * @param personType Type of person entered by user into command. (Case-insensitive)
     * @return The appropriate PersonType.
     * @throws ParseException If personType != "s" or "t" (Case-insensitive)
     */
    public static PersonType detectPersonType(String personType) throws ParseException {
        personType = personType.toLowerCase();
        if (personType.equals("t")) {
            return PersonType.TUTOR;
        } else if (personType.equals("s")) {
            return PersonType.STUDENT;
        } else {
            throw new ParseException(MESSAGE_INVALID_PERSON_TYPE);
        }
    }

    @Override
    public String toString() {
        String personType = super.toString().toLowerCase();
        return personType.substring(0, 1).toUpperCase() + personType.substring(1).toLowerCase();
    }
}
