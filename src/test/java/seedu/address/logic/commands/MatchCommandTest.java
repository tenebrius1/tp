package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;

public class MatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredStudentList_success() {
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_SUCCESS, studentToMatch);

        ModelManager expectedModelStudent = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelStudent.updateMatchedTutor(
                new TagsContainTagPredicate(Collections.singletonList(studentToMatch.getTag())));

        assertCommandSuccess(matchCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_validIndexUnfilteredStudentList_throwsCommandException() {
        // 3rd student in TypicalPerson.java has a tag that does not match with any tutors
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_THIRD_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_THIRD_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_FAILED, studentToMatch);

        assertCommandFailure(matchCommandStudent, model, expectedMessageStudent);
    }

    @Test
    public void execute_invalidIndexUnfilteredListStudent_throwsCommandException() {
        Index outOfBoundIndexStudent = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        MatchCommand matchCommandStudent = new MatchCommand(outOfBoundIndexStudent);

        assertCommandFailure(matchCommandStudent, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListStudent_success() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_SUCCESS, studentToMatch);

        Model expectedModelStudent = new ModelManager(model.getAddressBook(), new UserPrefs());
        showStudentAtIndex(expectedModelStudent, INDEX_FIRST_PERSON);
        expectedModelStudent.updateMatchedTutor(
                new TagsContainTagPredicate(Collections.singletonList(studentToMatch.getTag())));

        assertCommandSuccess(matchCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_validIndexFilteredStudentList_throwsCommandException() {
        // 3rd student in TypicalPerson.java has a tag that does not match with any tutors
        showStudentAtIndex(model, INDEX_THIRD_PERSON);

        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_FAILED, studentToMatch);

        assertCommandFailure(matchCommandStudent, model, expectedMessageStudent);
    }

    @Test
    public void execute_invalidIndexFilteredListStudent_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndexStudent = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndexStudent is still in bounds of address book list
        assertTrue(outOfBoundIndexStudent.getZeroBased() < model.getAddressBook().getStudentList().size());

        MatchCommand matchCommandStudent = new MatchCommand(outOfBoundIndexStudent);

        assertCommandFailure(matchCommandStudent, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final MatchCommand standardCommand = new MatchCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        MatchCommand commandWithSameValues = new MatchCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MatchCommand(INDEX_SECOND_PERSON)));
    }
}
