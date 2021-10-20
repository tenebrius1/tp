package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Represents a chained predicate to be used in FindCommand.
 */
public class ChainedPredicate implements Predicate<Person> {
    private final Name name;
    private final Gender gender;
    private final Qualification qualification;
    private final List<Tag> tags;
    private final Predicate<Person> predicate;

    /** Constructs a {@code ChainedPredicate}. */
    private ChainedPredicate(Name name, Gender gender, Qualification qualification, List<Tag> tags,
                            Predicate<Person> predicate) {
        this.name = name;
        this.gender = gender;
        this.qualification = qualification;
        this.tags = tags;
        this.predicate = predicate;
    }

    @Override
    public boolean test(Person person) {
        return predicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChainedPredicate // instanceof handles nulls
                        && gender.equals(((ChainedPredicate) other).gender)
                        && name.equals(((ChainedPredicate) other).name)
                        && qualification.equals(((ChainedPredicate) other).qualification)
                        && tags.equals(((ChainedPredicate) other).tags));
    }

    public static class Builder {
        private Name name = new Name("DEFAULT");
        private Gender gender = new Gender("M");
        private Qualification qualification = new Qualification("0");
        private List<Tag> tags = new ArrayList<>();
        private Predicate<Person> predicate;

        public Builder setName(Name name) {
            this.name = name;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder setQualification(Qualification qualification) {
            this.qualification = qualification;
            return this;
        }

        public Builder setTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder setPredicate(Predicate<Person> predicate) {
            this.predicate = predicate;
            return this;
        }

        public ChainedPredicate build() {
            return new ChainedPredicate(name, gender, qualification, tags, predicate);
        }
    }
}
