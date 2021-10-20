package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Gender} matches the Gender given.
 */
public class GenderContainsGenderPredicate implements Predicate<Person> {
    private final List<Gender> gender;

    /**
     * Constructs a {@code GenderContainsGenderPredicate}.
     *
     * @param gender Valid gender.
     */
    public GenderContainsGenderPredicate(List<Gender> gender) {
        this.gender = gender;
    }

    @Override
    public boolean test(Person person) {
        return gender.stream()
                .anyMatch(gender -> person.getGender().equals(gender));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenderContainsGenderPredicate // instanceof handles nulls
                && gender.equals(((GenderContainsGenderPredicate) other).gender)); // state check
    }
}
