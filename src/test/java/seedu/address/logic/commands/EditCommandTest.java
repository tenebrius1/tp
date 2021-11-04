package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showTutorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.EditCommand.EditTutorDescriptor;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.CliTutors;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EditTutorDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {
    private Model model = new ModelManager(getTypicalCliTutors(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredTutorList_success() {
        Tutor editedTutor = new TutorBuilder().build();
        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder(editedTutor).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor, PersonType.TUTOR);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTOR_SUCCESS, editedTutor);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setTutor(model.getFilteredTutorList().get(0), editedTutor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredStudentList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor, PersonType.STUDENT);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredTutorList_success() {
        Index indexLastTutor = Index.fromOneBased(model.getFilteredTutorList().size());
        Tutor lastTutor = model.getFilteredTutorList().get(indexLastTutor.getZeroBased());

        TutorBuilder personInList = new TutorBuilder(lastTutor);
        Tutor editedTutor = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_PM).build();

        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_PM).build();
        EditCommand editCommand = new EditCommand(indexLastTutor, descriptor, PersonType.TUTOR);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTOR_SUCCESS, editedTutor);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setTutor(lastTutor, editedTutor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredStudentList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder personInList = new StudentBuilder(lastStudent);
        Student editedStudent = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTag(VALID_TAG_PM, VALID_TAG_TP).build();

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_PM, VALID_TAG_TP).build();
        EditCommand editCommand = new EditCommand(indexLastStudent, descriptor, PersonType.STUDENT);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setStudent(lastStudent, editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredTutorList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTutorDescriptor(), PersonType.TUTOR);
        String expectedMessage = EditCommand.MESSAGE_UNCHANGED_TUTOR;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredStudentList_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditStudentDescriptor(), PersonType.STUDENT);
        String expectedMessage = EditCommand.MESSAGE_UNCHANGED_STUDENT;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredTutorList_success() {
        Tutor tutorInFilteredList = model.getFilteredTutorList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutor editedTutor = new TutorBuilder(tutorInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
            new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.TUTOR);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TUTOR_SUCCESS, editedTutor);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setTutor(model.getFilteredTutorList().get(0), editedTutor);

        // After the name change, expected filtered list should be empty.
        expectedModel.updateFilteredTutorList(x -> false);
        showTutorAtIndex(model, INDEX_FIRST_PERSON);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredStudentList_success() {
        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
            new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.STUDENT);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new CliTutors(model.getCliTutors()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        // After the name change, expected filtered list should be empty.
        expectedModel.updateFilteredStudentList(x -> false);
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTutorUnfilteredTutorList_failure() {
        Tutor firstTutor = model.getFilteredTutorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder(firstTutor).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor, PersonType.TUTOR);

        assertCommandFailure(editCommand, model, Phone.MESSAGE_REPEATED_PHONE);
    }

    @Test
    public void execute_duplicateStudentUnfilteredStudentList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor, PersonType.STUDENT);

        assertCommandFailure(editCommand, model, Phone.MESSAGE_REPEATED_PHONE);
    }

    @Test
    public void execute_duplicateTutorFilteredTutorList_failure() {
        showTutorAtIndex(model, INDEX_FIRST_PERSON);

        // edit tutor in filtered list into a duplicate in address book
        Tutor tutorInList = model.getCliTutors().getTutorList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorDescriptorBuilder(tutorInList).build(), PersonType.TUTOR);

        assertCommandFailure(editCommand, model, Phone.MESSAGE_REPEATED_PHONE);
    }

    @Test
    public void execute_duplicateStudentFilteredStudentList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        // edit student in filtered list into a duplicate in address book
        Student studentInList = model.getCliTutors().getStudentList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditStudentDescriptorBuilder(studentInList).build(), PersonType.STUDENT);

        assertCommandFailure(editCommand, model, Phone.MESSAGE_REPEATED_PHONE);
    }

    @Test
    public void execute_invalidTutorIndexUnfilteredTutorList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorList().size() + 1);
        EditTutorDescriptor descriptor = new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor, PersonType.TUTOR);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredStudentList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor, PersonType.STUDENT);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered tutor list where index is larger than size of filtered tutor list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTutorIndexFilteredTutorList_failure() {
        showTutorAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCliTutors().getTutorList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.TUTOR);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTOR_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered student list where index is larger than size of filtered student list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredStudentList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCliTutors().getStudentList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.STUDENT);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyStudentList_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.STUDENT);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_NO_PERSON);

        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT));
    }

    @Test
    public void execute_emptyTutorList_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorDescriptorBuilder().withName(VALID_NAME_BOB).build(), PersonType.TUTOR);
        model.updateFilteredTutorList(Model.PREDICATE_SHOW_NO_PERSON);

        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.TUTOR));
    }

    @Test
    public void equals() {
        final EditCommand standardTutorCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_BOB, PersonType.TUTOR);
        final EditCommand standardStudentCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY, PersonType.STUDENT);

        // same values -> returns true
        EditTutorDescriptor copyTutorDescriptor = new EditTutorDescriptor(DESC_BOB);
        EditCommand commandWithSameTutorValues = new EditCommand(INDEX_FIRST_PERSON,
            copyTutorDescriptor, PersonType.TUTOR);
        EditStudentDescriptor copyStudentDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditCommand commandWithSameStudentValues = new EditCommand(INDEX_FIRST_PERSON,
                copyStudentDescriptor, PersonType.STUDENT);
        assertTrue(standardTutorCommand.equals(commandWithSameTutorValues));
        assertTrue(standardStudentCommand.equals(commandWithSameStudentValues));

        // same object -> returns true
        assertTrue(standardTutorCommand.equals(standardTutorCommand));
        assertTrue(standardStudentCommand.equals(standardStudentCommand));

        // null -> returns false
        assertFalse(standardTutorCommand.equals(null));
        assertFalse(standardStudentCommand.equals(null));

        // different types -> returns false
        assertFalse(standardTutorCommand.equals(new ClearCommand()));
        assertFalse(standardStudentCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardTutorCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_BOB, PersonType.TUTOR)));
        assertFalse(standardStudentCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY, PersonType.STUDENT)));

        // different descriptor -> returns false
        assertFalse(standardTutorCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY, PersonType.TUTOR)));
        assertFalse(standardStudentCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB, PersonType.STUDENT)));
    }
}
