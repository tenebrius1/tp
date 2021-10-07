package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Tutor ALICE = new TutorBuilder().withName("Alice Pauline")
            .withGender("F").withPhone("94351253").withQualification("0").withTags("PM").build();
    public static final Tutor BENSON = new TutorBuilder().withName("Benson Meier")
            .withGender("M").withPhone("98765432").withQualification("1").withTags("SM", "SC").build();
    public static final Tutor CARL = new TutorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("M").withQualification("2").withTags("TL", "TE").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withTag("PM").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withGender("F").withTag("SC").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withGender("F").withTag("TG").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
            .withGender("M").withTag("TP").build();

    // Manually added
    public static final Tutor HOON = new TutorBuilder().withName("Hoon Meier").withPhone("8482424")
            .withGender("M").withQualification("2").withTags("TG", "TL").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withGender("F").withTag("PE").build();

    // Manually added - Students with similar names
    public static final Student JOHN_R = new StudentBuilder().withName("John Rhys").withPhone("9482423")
            .withGender("M").withTag("SC").build();
    public static final Student JOHN_P = new StudentBuilder().withName("Johnathan Prue")
            .withPhone("9482452").withGender("M").withTag("TP").build();

    // Manually added - Tutors with similar names
    public static final Tutor DON_A = new TutorBuilder().withName("Don Archie").withPhone("95398563")
            .withGender("M").withQualification("2").withTags("TL", "TE").build();
    public static final Tutor DON_E = new TutorBuilder().withName("Don Ethel").withPhone("95462563")
            .withGender("M").withQualification("3").withTags("TP", "SC").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withGender(VALID_GENDER_AMY).withTag(VALID_TAG_TP).build();
    public static final Tutor BOB = new TutorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB).withQualification(VALID_QUALIFICATION_BOB)
            .withTags(VALID_TAG_PM, VALID_TAG_TP).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Tutor tutor : getTypicalTutors()) {
            ab.addTutor(tutor);
        }
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBookWithSimilarNames() {
        AddressBook ab = new AddressBook();
        for (Tutor tutor : getTutorsWithSimilarNames()) {
            ab.addTutor(tutor);
        }
        for (Student student : getStudentsWithSimilarNames()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Tutor> getTypicalTutors() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Tutor> getTutorsWithSimilarNames() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DON_A, DON_E));
    }

    public static List<Student> getStudentsWithSimilarNames() {
        return new ArrayList<>(Arrays.asList(DANIEL, ELLE, FIONA, GEORGE, JOHN_P, JOHN_R));
    }
}
