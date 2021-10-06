package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DON_A;
import static seedu.address.testutil.TypicalPersons.DON_E;
import static seedu.address.testutil.TypicalPersons.JOHN_P;
import static seedu.address.testutil.TypicalPersons.JOHN_R;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithSimilarNames;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PersonType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.stubs.FindCommandParserStub;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model similarNamesModel = new ModelManager(getTypicalAddressBookWithSimilarNames(),
            new UserPrefs());
    private Model expectedSimilarNamesModel = new ModelManager(getTypicalAddressBookWithSimilarNames(),
            new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        PersonType tutor = PersonType.TUTOR;
        PersonType student = PersonType.STUDENT;

        FindCommand findFirstCommand = new FindCommand(firstPredicate, tutor);
        FindCommand findSecondCommand = new FindCommand(secondPredicate, student);
        FindCommand findThirdCommand = new FindCommand(firstPredicate, student);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same value and personType -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, tutor);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);

        // different personType -> returns false
        assertNotEquals(findFirstCommand, findThirdCommand);
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = null;
        try {
            predicate = preparePredicate("n/ ");
        } catch (ParseException e) {
            fail();
        }
        FindCommand command = new FindCommand(predicate, PersonType.STUDENT);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_zeroKeywords_noTutorFound() {
        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = null;
        try {
            predicate = preparePredicate("n/ ");
        } catch (ParseException e) {
            fail();
        }
        FindCommand command = new FindCommand(predicate, PersonType.TUTOR);
        expectedModel.updateFilteredTutorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTutorList());
    }

    @Test
    public void execute_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        try {
            NameContainsKeywordsPredicate predicate = preparePredicate("n/john");
            FindCommand command = new FindCommand(predicate, PersonType.STUDENT);
            expectedSimilarNamesModel.updateFilteredStudentList(predicate);
            assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
            assertEquals(Arrays.asList(JOHN_P, JOHN_R), similarNamesModel.getFilteredStudentList());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void execute_multipleTutorsFound() {
        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 2);
        try {
            NameContainsKeywordsPredicate predicate = preparePredicate("n/Don");
            FindCommand command = new FindCommand(predicate, PersonType.TUTOR);
            expectedSimilarNamesModel.updateFilteredTutorList(predicate);
            assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
            assertEquals(Arrays.asList(DON_A, DON_E), model.getFilteredTutorList());
        } catch (ParseException e) {
            fail();
        }
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) throws ParseException {
        Name name = FindCommandParserStub.extractName(userInput);
        String[] nameList = new String[] {name.toString()};
        return new NameContainsKeywordsPredicate(Arrays.asList(nameList));
    }
}
