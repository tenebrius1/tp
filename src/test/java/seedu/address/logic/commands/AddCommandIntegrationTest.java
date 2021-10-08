package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTutor_success() {
        Tutor validTutor = new TutorBuilder().build();

        Model expectedModelTutor = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelTutor.addTutor(validTutor);

        assertCommandSuccess(new AddCommand(validTutor, PersonType.TUTOR), model,
                String.format(AddCommand.MESSAGE_SUCCESS_TUTOR, validTutor), expectedModelTutor);
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModelStudent = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelStudent.addStudent(validStudent);

        assertCommandSuccess(new AddCommand(validStudent, PersonType.STUDENT), model,
                String.format(AddCommand.MESSAGE_SUCCESS_STUDENT, validStudent), expectedModelStudent);
    }

    @Test
    public void execute_duplicateTutor_throwsCommandException() {
        Tutor tutorInList = model.getAddressBook().getTutorList().get(0);

        assertCommandFailure(new AddCommand(tutorInList, PersonType.TUTOR),
                model, AddCommand.MESSAGE_DUPLICATE_TUTOR);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getAddressBook().getStudentList().get(0);

        assertCommandFailure(new AddCommand(studentInList, PersonType.STUDENT),
                model, AddCommand.MESSAGE_DUPLICATE_STUDENT);
    }
}
