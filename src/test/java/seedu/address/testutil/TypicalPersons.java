package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CliTutors;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * A utility class containing a list of {@code Tutor} and {@code Student} objects to be used in tests.
 */
public class TypicalPersons {
    // Tutors
    public static final Tutor ALICE = new TutorBuilder().withName("Alice Pauline")
            .withGender("F").withPhone("94351253").withQualification("0").withTags("PM").build();
    public static final Tutor BENSON = new TutorBuilder().withName("Benson Meier")
            .withGender("M").withPhone("98765432").withQualification("1")
            .withRemark("Wants student in Ang Mo Kio").withTags("SM", "SC").build();
    public static final Tutor CARL = new TutorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("M").withQualification("2").withTags("TL", "TE").build();

    // Students
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withTag("PM", "PE").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("94822240")
            .withGender("F").withRemark("Wants tutor in Bishan").withTag("SC", "SB", "SM").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("94824270")
            .withGender("F").withTag("TG").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("94824420")
            .withGender("M").withTag("TP").build();

    // Manually added
    public static final Tutor HOON = new TutorBuilder().withName("Hoon Meier").withPhone("84824242")
            .withGender("M").withQualification("2").withTags("TG", "TL").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("84821313")
            .withGender("F").withTag("PE").build();

    // Manually added - Students with similar names
    public static final Student JOHN_R = new StudentBuilder().withName("John Rhys").withPhone("94824231")
            .withGender("M").withTag("TP").build();
    public static final Student JOHN_P = new StudentBuilder().withName("John Prue")
            .withPhone("94824526").withGender("M").withTag("TP").build();

    // Manually added - Tutors with similar names
    public static final Tutor DON_A = new TutorBuilder().withName("Don Archie").withPhone("95398563")
            .withGender("M").withQualification("2").withTags("TL", "TE").build();
    public static final Tutor DON_E = new TutorBuilder().withName("Don Ethel").withPhone("95462563")
            .withGender("M").withQualification("3").withTags("TP", "SC").build();

    // Manually added - Student/Tutor details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withGender(VALID_GENDER_AMY).withRemark(VALID_REMARK_AMY).withTag(VALID_TAG_TP).build();
    public static final Tutor BOB = new TutorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB).withQualification(VALID_QUALIFICATION_BOB).withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_PM, VALID_TAG_TP).build();

    // Manually added - Testing FilterCommand
    public static final Student JACKSON = new StudentBuilder().withName("Jackson King").withPhone("90909839")
            .withGender("M").withTag("PM").build();
    public static final Tutor ENZIO = new TutorBuilder().withName("Enzio Lee").withPhone("95092183")
            .withGender("M").withQualification("2").withTags("PM", "TL", "TC").build();
    public static final Tutor MICHAEL = new TutorBuilder().withName("Michael Chen").withPhone("89102932")
            .withGender("M").withQualification("3").withTags("PM", "PS").build();
    public static final Tutor ROXANNE = new TutorBuilder().withName("Roxanne Tan").withPhone("91119222")
            .withGender("F").withQualification("2").withTags("PM", "SG").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code CliTutors} with all the typical persons.
     */
    public static CliTutors getTypicalCliTutors() {
        CliTutors ab = new CliTutors();
        for (Tutor tutor : getTypicalTutors()) {
            ab.addTutor(tutor);
        }
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns an {@code CliTutors} with all the persons with similar names.
     */
    public static CliTutors getTypicalCliTutorsWithSimilarNames() {
        CliTutors ab = new CliTutors();
        for (Tutor tutor : getTutorsWithSimilarNames()) {
            ab.addTutor(tutor);
        }
        for (Student student : getStudentsWithSimilarNames()) {
            ab.addStudent(student);
        }
        return ab;
    }

    /**
     * Returns an {@code CliTutors} with all the persons for FilterCommand Test
     */
    public static CliTutors getTypicalCliTutorsForFilterTest() {
        CliTutors ab = new CliTutors();
        for (Tutor tutor : getTutorsForFilterCommandTest()) {
            ab.addTutor(tutor);
        }
        for (Student student : getStudentsForFilterCommandTest()) {
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

    public static List<Tutor> getTutorsForFilterCommandTest() {
        return new ArrayList<>(Arrays.asList(ENZIO, MICHAEL, ROXANNE));
    }

    public static List<Student> getStudentsForFilterCommandTest() {
        return new ArrayList<>(Arrays.asList(JACKSON));
    }
}
