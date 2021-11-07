package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_UNIVERSITY_STUDENT;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TutorBuilder;

public class QualificationContainsQualificationPredicateTest {
    @Test
    public void equals() {
        List<Qualification> firstQualificationList =
                Collections.singletonList(new Qualification(VALID_QUALIFICATION_BOB));
        List<Qualification> secondQualificationList =
                List.of(new Qualification(VALID_QUALIFICATION_UNIVERSITY_STUDENT));

        QualificationContainsQualificationPredicate firstPredicate =
                new QualificationContainsQualificationPredicate(firstQualificationList);
        QualificationContainsQualificationPredicate secondPredicate =
                new QualificationContainsQualificationPredicate(secondQualificationList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QualificationContainsQualificationPredicate firstPredicateCopy =
                new QualificationContainsQualificationPredicate(firstQualificationList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tutorQualificationContainsQualification_returnsTrue() {
        // One Tag
        QualificationContainsQualificationPredicate predicate =
                new QualificationContainsQualificationPredicate(
                        Collections.singletonList(new Qualification(VALID_QUALIFICATION_BOB)));
        assertTrue(predicate.test(new TutorBuilder().withQualification(VALID_QUALIFICATION_BOB).build()));
    }

    @Test
    public void test_tutorQualificationDoesNotContainsQualification_returnsFalse() {
        QualificationContainsQualificationPredicate predicate =
                new QualificationContainsQualificationPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorBuilder().withQualification(VALID_QUALIFICATION_BOB).build()));

        // Non-matching keyword
        predicate =
                new QualificationContainsQualificationPredicate(List.of(new Qualification(VALID_QUALIFICATION_BOB)));
        assertFalse(predicate.test(new TutorBuilder().withQualification(
                VALID_QUALIFICATION_UNIVERSITY_STUDENT).build()));

        // test a student
        assertFalse(predicate.test(DANIEL));
    }
}
