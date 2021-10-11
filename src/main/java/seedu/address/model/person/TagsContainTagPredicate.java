package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

public class TagsContainTagPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    /**
     * Constructs a {@code TagsContainTagPredicate}.
     *
     * @param tags Valid keywords.
     */
    public TagsContainTagPredicate(List<Tag> tags) {
        this.tags = tags;
    }

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
