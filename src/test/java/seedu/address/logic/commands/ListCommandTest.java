package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showTutorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_tutorListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PersonType.TUTOR), model, ListCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_studentListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PersonType.STUDENT), model, ListCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }

    @Test
    public void execute_tutorListIsFiltered_showsEverything() {
        // Temporarily only shows first tutor
        showTutorAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(PersonType.TUTOR), model, ListCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_studentListIsFiltered_showsEverything() {
        // Temporarily only shows first student
        showStudentAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(PersonType.STUDENT), model, ListCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }
}
