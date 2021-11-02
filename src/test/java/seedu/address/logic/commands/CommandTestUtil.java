package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUALIFICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.CliTutors;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EditTutorDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_ALICE = "ALICE";
    public static final String VALID_NAME_ROXANNE = "Roxanne";
    public static final String INVALID_NAME_ELON = "el$n mUsk";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_GENDER_BOB = "M";
    public static final String VALID_REMARK_AMY = "Prefers home-based lessons";
    public static final String VALID_REMARK_BOB = "Wants students in Bishan";
    public static final String VALID_QUALIFICATION_BOB = "0";
    public static final String VALID_QUALIFICATION_UNIVERSITY_STUDENT = "1";
    public static final String VALID_QUALIFICATION_GRADUATE = "2";
    public static final String VALID_QUALIFICATION_MOE = "3";
    public static final String VALID_TAG_PM = "PM";
    public static final String VALID_TAG_TP = "TP";
    public static final String VALID_TAG_TP_UNCAPITALIZED = "tp";
    public static final PersonType VALID_TUTOR_TYPE = PersonType.TUTOR;
    public static final PersonType VALID_STUDENT_TYPE = PersonType.STUDENT;
    public static final String VALID_STUDENT_LETTER = "s";
    public static final String VALID_TUTOR_LETTER = "t";
    public static final String LETTER_DESC_STUDENT = " " + VALID_STUDENT_LETTER;
    public static final String LETTER_DESC_TUTOR = " " + VALID_TUTOR_LETTER;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_NAME_ALICE;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String QUALIFICATION_DESC_BOB = " " + PREFIX_QUALIFICATION + VALID_QUALIFICATION_BOB;
    public static final String TAG_DESC_PM_TP = " " + PREFIX_TAG + VALID_TAG_PM + " " + VALID_TAG_TP;
    public static final String TAG_DESC_PM = " " + PREFIX_TAG + VALID_TAG_PM;
    public static final String TAG_DESC_TP = " " + PREFIX_TAG + VALID_TAG_TP;
    public static final String TAG_DESC_UNCAPITALIZED = " " + PREFIX_TAG + VALID_TAG_TP_UNCAPITALIZED;

    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "Z"; // 'Z' not allowed in gender
    public static final String INVALID_QUALIFICATION_DESC = " " + PREFIX_QUALIFICATION + "4"; // invalid qualification
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + " "; // ' ' not allowed in remark
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "ABCDE"; //  invalid tag argument
    public static final String INVALID_PREAMBLE = "A"; // only 't' or 's' allowed
    public static final String INVALID_TAG = "ABCDE";
    public static final String INVALID_ZERO_INDEX = "0";
    public static final String INVALID_INDEX = "-69";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditTutorDescriptor DESC_BOB;

    static {
        // Amy is a Student
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withGender(VALID_GENDER_AMY)
                .withRemark(VALID_REMARK_AMY)
                .withTags(VALID_TAG_PM).build();
        // Bob is a Tutor
        DESC_BOB = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_BOB)
                .withQualification(VALID_QUALIFICATION_BOB)
                .withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_PM, VALID_TAG_TP).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CliTutors expectedCliTutors = new CliTutors(actualModel.getCliTutors());
        List<Tutor> expectedFilteredTutorList = new ArrayList<>(actualModel.getFilteredTutorList());
        List<Student> expectedFilteredStudentList = new ArrayList<>(actualModel.getFilteredStudentList());
        List<Tutor> expectedMatchedTutorList = new ArrayList<>(actualModel.getMatchedTutorList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCliTutors, actualModel.getCliTutors());
        assertEquals(expectedFilteredTutorList, actualModel.getFilteredTutorList());
        assertEquals(expectedFilteredStudentList, actualModel.getFilteredStudentList());
        assertEquals(expectedMatchedTutorList, actualModel.getMatchedTutorList());
    }

    /**
     * Updates {@code model}'s filtered tutor list to show only the tutor at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTutorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTutorList().size());

        Person person = model.getFilteredTutorList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredTutorList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredTutorList().size());
    }

    /**
     * Updates {@code model}'s filtered student list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Person person = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }
}
