package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PersonType;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBookClearTutor_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(PersonType.TUTOR), model, ClearCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_emptyAddressBookClearStudent_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(PersonType.STUDENT), model, ClearCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookClearTutor_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setTutorData(new AddressBook());

        assertCommandSuccess(new ClearCommand(PersonType.TUTOR), model, ClearCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookClearStudent_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setStudentData(new AddressBook());

        assertCommandSuccess(new ClearCommand(PersonType.STUDENT), model, ClearCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }
}
