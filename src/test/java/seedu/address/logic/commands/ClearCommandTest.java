package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PersonType;
import seedu.address.model.CliTutors;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {
    @Test
    public void execute_emptyCliTutorsClearTutor_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(PersonType.TUTOR), model, ClearCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_emptyCliTutorsClearStudent_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(PersonType.STUDENT), model, ClearCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyCliTutorsClearTutor_success() {
        Model model = new ModelManager(getTypicalCliTutors(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCliTutors(), new UserPrefs());
        expectedModel.setTutorData(new CliTutors());

        assertCommandSuccess(new ClearCommand(PersonType.TUTOR), model, ClearCommand.MESSAGE_SUCCESS_TUTOR,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyCliTutorsClearStudent_success() {
        Model model = new ModelManager(getTypicalCliTutors(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCliTutors(), new UserPrefs());
        expectedModel.setStudentData(new CliTutors());

        assertCommandSuccess(new ClearCommand(PersonType.STUDENT), model, ClearCommand.MESSAGE_SUCCESS_STUDENT,
                expectedModel);
    }
}
