package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showTutorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredListTutor_success() {
        Tutor tutorToDelete = model.getFilteredTutorList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommandTutor = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.TUTOR);
        String expectedMessageTutor = String.format(DeleteCommand.MESSAGE_DELETE_TUTOR_SUCCESS, tutorToDelete);
        ModelManager expectedModelTutor = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelTutor.deleteTutor(tutorToDelete);

        assertCommandSuccess(deleteCommandTutor, model, expectedMessageTutor, expectedModelTutor);
    }

    @Test
    public void execute_validIndexUnfilteredListStudent_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommandStudent = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.STUDENT);
        String expectedMessageStudent = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);
        ModelManager expectedModelStudent = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelStudent.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_invalidIndexUnfilteredListTutor_throwsCommandException() {
        Index outOfBoundIndexTutor = Index.fromOneBased(model.getFilteredTutorList().size() + 1);
        DeleteCommand deleteCommandTutor = new DeleteCommand(outOfBoundIndexTutor, PersonType.TUTOR);

        assertCommandFailure(deleteCommandTutor, model, Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredListStudent_throwsCommandException() {
        Index outOfBoundIndexStudent = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteCommand deleteCommandStudent = new DeleteCommand(outOfBoundIndexStudent, PersonType.STUDENT);

        assertCommandFailure(deleteCommandStudent, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListTutor_success() {
        showTutorAtIndex(model, INDEX_FIRST_PERSON);

        Tutor tutorToDelete = model.getFilteredTutorList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommandTutor = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.TUTOR);

        String expectedMessageTutor = String.format(DeleteCommand.MESSAGE_DELETE_TUTOR_SUCCESS, tutorToDelete);

        Model expectedModelTutor = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelTutor.deleteTutor(tutorToDelete);
        showNoTutor(expectedModelTutor);

        assertCommandSuccess(deleteCommandTutor, model, expectedMessageTutor, expectedModelTutor);
    }

    @Test
    public void execute_validIndexFilteredListStudent_success() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommandStudent = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.STUDENT);

        String expectedMessageStudent = String.format(DeleteCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        Model expectedModelStudent = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelStudent.deleteStudent(studentToDelete);
        showNoStudent(expectedModelStudent);

        assertCommandSuccess(deleteCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_invalidIndexFilteredListTutor_throwsCommandException() {
        showTutorAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndexTutor = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndexTutor is still in bounds of address book list
        assertTrue(outOfBoundIndexTutor.getZeroBased() < model.getAddressBook().getTutorList().size());

        DeleteCommand deleteCommandTutor = new DeleteCommand(outOfBoundIndexTutor, PersonType.TUTOR);

        assertCommandFailure(deleteCommandTutor, model, Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredListStudent_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndexStudent = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndexStudent is still in bounds of address book list
        assertTrue(outOfBoundIndexStudent.getZeroBased() < model.getAddressBook().getStudentList().size());

        DeleteCommand deleteCommandStudent = new DeleteCommand(outOfBoundIndexStudent, PersonType.STUDENT);

        assertCommandFailure(deleteCommandStudent, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommandTutor = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.TUTOR);
        DeleteCommand deleteSecondCommandTutor = new DeleteCommand(INDEX_SECOND_PERSON, PersonType.TUTOR);

        DeleteCommand deleteFirstCommandStudent = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.STUDENT);
        DeleteCommand deleteSecondCommandStudent = new DeleteCommand(INDEX_SECOND_PERSON, PersonType.STUDENT);

        // same object -> returns true
        assertTrue(deleteFirstCommandTutor.equals(deleteFirstCommandTutor));
        assertTrue(deleteFirstCommandStudent.equals(deleteFirstCommandStudent));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopyTutor = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.TUTOR);
        assertTrue(deleteFirstCommandTutor.equals(deleteFirstCommandCopyTutor));
        DeleteCommand deleteFirstCommandCopyStudent = new DeleteCommand(INDEX_FIRST_PERSON, PersonType.STUDENT);
        assertTrue(deleteFirstCommandStudent.equals(deleteFirstCommandCopyStudent));

        // different types -> returns false
        assertFalse(deleteFirstCommandTutor.equals(1));
        assertFalse(deleteFirstCommandStudent.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandTutor.equals(null));
        assertFalse(deleteFirstCommandStudent.equals(null));

        // different tutors -> returns false
        assertFalse(deleteFirstCommandTutor.equals(deleteSecondCommandTutor));
        // different students -> returns false
        assertFalse(deleteFirstCommandStudent.equals(deleteSecondCommandStudent));
    }

    /**
     * Updates {@code model}'s filtered list to show no one, for tutors.
     */
    private void showNoTutor(Model model) {
        model.updateFilteredTutorList(p -> false);

        assertTrue(model.getFilteredTutorList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no one, for students.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
