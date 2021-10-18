package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.TutorBuilder;

public class TagsContainTagPredicateTest {
    @Test
    public void equals() {
        List<Tag> oneTagList = Collections.singletonList(new Tag(VALID_TAG_PM));
        List<Tag> twoTagsList = Arrays.asList(new Tag(VALID_TAG_PM), new Tag(VALID_TAG_TP));

        TagsContainTagPredicate oneTagPredicate = new TagsContainTagPredicate(oneTagList);
        TagsContainTagPredicate twoTagsPredicate = new TagsContainTagPredicate(twoTagsList);

        // same object -> returns true
        assertTrue(oneTagPredicate.equals(oneTagPredicate));

        // same values -> returns true
        TagsContainTagPredicate firstPredicateCopy = new TagsContainTagPredicate(oneTagList);
        assertTrue(oneTagPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(oneTagPredicate.equals(1));

        // null -> returns false
        assertFalse(oneTagPredicate.equals(null));

        // different person -> returns false
        assertFalse(oneTagPredicate.equals(twoTagsPredicate));
    }

    @Test
    public void test_tutorTagsContainsTag_returnsTrue() {
        // One Tag
        TagsContainTagPredicate predicate =
                new TagsContainTagPredicate(Collections.singletonList(new Tag(VALID_TAG_PM)));
        assertTrue(predicate.test(new TutorBuilder().withTags(VALID_TAG_PM).build()));

        // Tutor have multiple tags
        assertTrue(predicate.test(new TutorBuilder().withTags(VALID_TAG_PM, VALID_TAG_TP).build()));
    }

    @Test
    public void test_tutorTagsDoesNotContainTag_returnsFalse() {
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorBuilder().withTags(VALID_TAG_PM).build()));

        // Non-matching keyword
        predicate = new TagsContainTagPredicate(Arrays.asList(new Tag(VALID_TAG_PM)));
        assertFalse(predicate.test(new TutorBuilder().withTags(VALID_TAG_TP).build()));
    }
}
