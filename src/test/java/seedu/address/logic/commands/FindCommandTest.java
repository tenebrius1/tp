package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_GRADUATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_MOE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DON_A;
import static seedu.address.testutil.TypicalPersons.DON_E;
import static seedu.address.testutil.TypicalPersons.JOHN_P;
import static seedu.address.testutil.TypicalPersons.JOHN_R;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutorsWithSimilarNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.QualificationContainsQualificationPredicate;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalCliTutors(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCliTutors(), new UserPrefs());
    private Model similarNamesModel = new ModelManager(getTypicalCliTutorsWithSimilarNames(),
            new UserPrefs());
    private Model expectedSimilarNamesModel = new ModelManager(getTypicalCliTutorsWithSimilarNames(),
            new UserPrefs());

    @Test
    public void execute_multipleStudentsFound_success() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("john");
        TagsContainTagPredicate tagPredicate = prepareTagPredicate("TP");
        FindCommand command = new FindCommand(tagPredicate.and(namePredicate), PersonType.STUDENT);
        expectedSimilarNamesModel.updateFilteredStudentList(tagPredicate.and(namePredicate));
        assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
        assertEquals(Arrays.asList(JOHN_P, JOHN_R), similarNamesModel.getFilteredStudentList());
    }

    @Test
    public void execute_multipleTutorsFound_success() {
        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Don");
        GenderContainsGenderPredicate genderPredicate = prepareGenderPredicate(VALID_GENDER_BOB);
        FindCommand command = new FindCommand(genderPredicate.and(namePredicate), PersonType.TUTOR);
        expectedSimilarNamesModel.updateFilteredTutorList(genderPredicate.and(namePredicate));
        assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
        assertEquals(Arrays.asList(DON_A, DON_E), similarNamesModel.getFilteredTutorList());
    }

    @Test
    public void execute_noTutorsFound_success() {
        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 0);
        QualificationContainsQualificationPredicate predicate = prepareQualificationPredicate("3");
        FindCommand command = new FindCommand(predicate, PersonType.TUTOR);
        expectedModel.updateFilteredTutorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<Tutor>(), model.getFilteredTutorList());
    }

    @Test
    public void execute_noStudentsFound_success() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Xin Yan");
        FindCommand command = new FindCommand(predicate, PersonType.STUDENT);
        expectedSimilarNamesModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
        assertEquals(new ArrayList<Student>(), similarNamesModel.getFilteredStudentList());
    }

    @Test
    public void execute_emptyStudentList_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Alex Yeoh");
        FindCommand command = new FindCommand(predicate, PersonType.STUDENT);
        assertCommandFailure(command, new ModelManager(), expectedMessage);
    }

    @Test
    public void execute_emptyTutorList_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.TUTOR);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("David Li");
        FindCommand command = new FindCommand(predicate, PersonType.TUTOR);
        assertCommandFailure(command, new ModelManager(), expectedMessage);
    }

    @Test
    public void equals() {
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
        Gender gender = new Gender(VALID_GENDER_AMY);
        Qualification qualification = new Qualification(VALID_QUALIFICATION_GRADUATE);
        builder.setGender(gender);
        builder.setQualification(qualification);
        Predicate<Person> firstPredicate = builder.build();

        builder = new ChainedPredicate.Builder();
        qualification = new Qualification(VALID_QUALIFICATION_MOE);
        builder.setQualification(qualification);
        Predicate<Person> secondPredicate = builder.build();

        FindCommand findFirstCommand = new FindCommand(firstPredicate, PersonType.TUTOR);
        FindCommand findSecondCommand = new FindCommand(secondPredicate, PersonType.TUTOR);
        FindCommand findThirdCommand = new FindCommand(firstPredicate, PersonType.STUDENT);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same value and personType -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, PersonType.TUTOR);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different parameters -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different personType -> returns false
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsNamePredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code GenderContainsGenderPredicate}.
     */
    private GenderContainsGenderPredicate prepareGenderPredicate(String userInput) {
        return new GenderContainsGenderPredicate(Arrays.asList(new Gender(userInput)));
    }

    /**
     * Parses {@code userInput} into a {@code GenderContainsGenderPredicate}.
     */
    private QualificationContainsQualificationPredicate prepareQualificationPredicate(String userInput) {
        return new QualificationContainsQualificationPredicate(Arrays.asList(new Qualification(userInput)));
    }

    /**
     * Parses {@code userInput} into a {@code TagsContainsTagPredicate}.
     */
    private TagsContainTagPredicate prepareTagPredicate(String userInput) {
        return new TagsContainTagPredicate(Arrays.asList(new Tag(userInput)));
    }
}
