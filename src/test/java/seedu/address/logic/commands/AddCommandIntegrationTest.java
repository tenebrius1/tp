package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCliTutors(), new UserPrefs());
    }

    @Test
    public void execute_newTutor_success() {
        Tutor validTutor = new TutorBuilder().build();

        Model expectedModelTutor = new ModelManager(model.getCliTutors(), new UserPrefs());
        expectedModelTutor.addTutor(validTutor);

        assertCommandSuccess(new AddCommand(validTutor, PersonType.TUTOR), model,
                String.format(AddCommand.MESSAGE_SUCCESS_TUTOR, validTutor), expectedModelTutor);
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModelStudent = new ModelManager(model.getCliTutors(), new UserPrefs());
        expectedModelStudent.addStudent(validStudent);

        assertCommandSuccess(new AddCommand(validStudent, PersonType.STUDENT), model,
                String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, validStudent), expectedModelStudent);
    }

    @Test
    public void execute_duplicateTutor_throwsCommandException() {
        Tutor tutorInList = model.getCliTutors().getTutorList().get(INDEX_FIRST_PERSON.getZeroBased());

        assertCommandFailure(new AddCommand(tutorInList, PersonType.TUTOR),
                model, AddCommand.MESSAGE_DUPLICATE_TUTOR);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getCliTutors().getStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        assertCommandFailure(new AddCommand(studentInList, PersonType.STUDENT),
                model, AddCommand.MESSAGE_DUPLICATE_STUDENT);
    }
}
