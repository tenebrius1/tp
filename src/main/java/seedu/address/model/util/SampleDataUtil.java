package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Tutor[] getSampleTutors() {
        return new Tutor[] {
            new Tutor(new Name("Alex Yeoh"), new Phone("87438807"), new Gender("M"),
                new Qualification("0"), new Remark("Prefers tutoring in the East"), getTagSet("PM")),
            new Tutor(new Name("Bernice Yu"), new Phone("99272758"), new Gender("F"),
                new Qualification("1"), new Remark(), getTagSet("SP", "SC")),
            new Tutor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Gender("F"),
                new Qualification("2"), new Remark("Only does remote tuition"), getTagSet("TE", "TL"))
        };
    }

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("David Li"), new Phone("91031282"), new Gender("M"),
                    new Remark("Wants experienced tutor"), getTagSet("PM")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Gender("M"),
                    new Remark(), getTagSet("SC")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Gender("M"),
                    new Remark("Prefers lessons in public spaces"), getTagSet("TG", "TB"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Tutor sampleTutor : getSampleTutors()) {
            sampleAb.addTutor(sampleTutor);
        }
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
