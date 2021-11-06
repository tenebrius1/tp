package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the Tags given.
 */
public class TagsContainTagPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    /**
     * Constructs a {@code TagsContainTagPredicate}.
     *
     * @param tags Valid keywords.
     */
    public TagsContainTagPredicate(List<Tag> tags) {
        requireNonNull(tags);
        this.tags = tags;
    }

    /**
     * Returns True if person we are testing with have the same tag.
     *
     * @param person Person in the list to test.
     * @return True if person contains the tag in the list of tags, False otherwise.
     */
    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(tag -> person.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainTagPredicate // instanceof handles nulls
                && tags.equals(((TagsContainTagPredicate) other).tags)); // state check
    }
}
