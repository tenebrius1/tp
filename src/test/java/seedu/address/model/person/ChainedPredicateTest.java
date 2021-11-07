package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_UNIVERSITY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class ChainedPredicateTest {
    private Gender gender = new Gender(VALID_GENDER_AMY);
    private Name name = new Name(VALID_NAME_BOB);
    private Qualification qualification = new Qualification(VALID_QUALIFICATION_UNIVERSITY_STUDENT);
    private List<Tag> twoTagsList = Arrays.asList(new Tag(VALID_TAG_PM), new Tag(VALID_TAG_TP));
    private Predicate<Person> predicate = x -> true;

    @Test
    public void equals() {
        ChainedPredicate firstPredicate = new ChainedPredicate.Builder()
                .setGender(gender).setPredicate(predicate).build();
        ChainedPredicate secondPredicate = new ChainedPredicate.Builder()
                .setGender(gender).setPredicate(predicate).build();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same gender only -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // same name, gender -> returns true
        firstPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setPredicate(predicate).build();
        secondPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setPredicate(predicate).build();
        assertTrue(firstPredicate.equals(secondPredicate));

        // same name, gender, qualification -> returns true
        firstPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification)
                .setPredicate(predicate).build();
        secondPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification)
                .setPredicate(predicate).build();
        assertTrue(firstPredicate.equals(secondPredicate));

        // same name, gender, qualification, tags -> returns true
        firstPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification).setTags(twoTagsList)
                .setPredicate(predicate).build();
        secondPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification).setTags(twoTagsList)
                .setPredicate(predicate).build();
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // set up different parameters ChainedPredicates
        firstPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification).setTags(twoTagsList)
                .setPredicate(predicate).build();
        secondPredicate = new ChainedPredicate.Builder()
                .setGender(gender).setQualification(qualification).setTags(twoTagsList)
                .setPredicate(predicate).build();
        ChainedPredicate thirdPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setTags(twoTagsList)
                .setPredicate(predicate).build();
        ChainedPredicate fourthPredicate = new ChainedPredicate.Builder()
                .setName(name).setGender(gender).setQualification(qualification)
                .setPredicate(predicate).build();

        // Different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different qualification -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        // Different tags -> returns false
        assertFalse(firstPredicate.equals(fourthPredicate));
    }
}
