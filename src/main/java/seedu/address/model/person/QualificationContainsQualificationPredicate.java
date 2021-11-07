package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Qualification} matches the qualification given.
 */
public class QualificationContainsQualificationPredicate implements Predicate<Person> {
    private final List<Qualification> qualification;

    /**
     * Constructs a {@code GenderContainsGenderPredicate}.
     *
     * @param qualification Valid gender.
     */
    public QualificationContainsQualificationPredicate(List<Qualification> qualification) {
        this.qualification = qualification;
    }

    @Override
    public boolean test(Person person) {
        // Only tutors have qualifications
        if (person instanceof Tutor) {
            Tutor tutor = (Tutor) person;
            return qualification.stream()
                    .anyMatch(qualification -> tutor.getQualification().equals(qualification));
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QualificationContainsQualificationPredicate // instanceof handles nulls
                && qualification.equals(((QualificationContainsQualificationPredicate) other).qualification));
    }
}
