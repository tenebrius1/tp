package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DON_A;
import static seedu.address.testutil.TypicalPersons.DON_E;
import static seedu.address.testutil.TypicalPersons.JOHN_P;
import static seedu.address.testutil.TypicalPersons.JOHN_R;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithSimilarNames;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
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
    public void execute_multipleStudentsFound_success() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("john");
        FindCommand command = new FindCommand(predicate, PersonType.STUDENT);
        expectedSimilarNamesModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
        assertEquals(Arrays.asList(JOHN_P, JOHN_R), similarNamesModel.getFilteredStudentList());
    }

    @Test
    public void execute_multipleTutorsFound_success() {
        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("Don");
        FindCommand command = new FindCommand(predicate, PersonType.TUTOR);
        expectedSimilarNamesModel.updateFilteredTutorList(predicate);
        assertCommandSuccess(command, similarNamesModel, expectedMessage, expectedSimilarNamesModel);
        assertEquals(Arrays.asList(DON_A, DON_E), similarNamesModel.getFilteredTutorList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
