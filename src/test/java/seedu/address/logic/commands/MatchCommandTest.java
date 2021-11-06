package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.PersonType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.tag.Tag;

public class MatchCommandTest {
    private final Model model = new ModelManager(getTypicalCliTutors(), new UserPrefs());

    @Test
    public void execute_throwsNullPointerException() {
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);
        assertThrows(NullPointerException.class, () -> matchCommandStudent.execute(null));
    }

    @Test
    public void execute_validIndexUnfilteredStudentList_success() {
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_SUCCESS, studentToMatch.getName());

        ModelManager expectedModelStudent = new ModelManager(model.getCliTutors(), new UserPrefs());

        List<Tag> ls = new ArrayList<>(studentToMatch.getTags());

        expectedModelStudent.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls,
                studentToMatch);

        assertCommandSuccess(matchCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_validIndexUnfilteredStudentList_throwsCommandException() {
        // 3rd student in TypicalPerson.java has a tag that does not match with any tutors
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_THIRD_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_THIRD_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_FAILED, studentToMatch.getName());

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

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_SUCCESS, studentToMatch.getName());

        Model expectedModelStudent = new ModelManager(model.getCliTutors(), new UserPrefs());
        showStudentAtIndex(expectedModelStudent, INDEX_FIRST_PERSON);


        List<Tag> ls = new ArrayList<>(studentToMatch.getTags());

        expectedModelStudent.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls,
                studentToMatch);

        assertCommandSuccess(matchCommandStudent, model, expectedMessageStudent, expectedModelStudent);
    }

    @Test
    public void execute_validIndexFilteredStudentList_throwsCommandException() {
        // 3rd student in TypicalPerson.java has a tag that does not match with any tutors
        showStudentAtIndex(model, INDEX_THIRD_PERSON);

        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        MatchCommand matchCommandStudent = new MatchCommand(INDEX_FIRST_PERSON);

        String expectedMessageStudent = String.format(MatchCommand.MESSAGE_MATCHED_FAILED, studentToMatch.getName());

        assertCommandFailure(matchCommandStudent, model, expectedMessageStudent);
    }

    @Test
    public void execute_invalidIndexFilteredListStudent_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndexStudent = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndexStudent is still in bounds of address book list
        assertTrue(outOfBoundIndexStudent.getZeroBased() < model.getCliTutors().getStudentList().size());

        MatchCommand matchCommandStudent = new MatchCommand(outOfBoundIndexStudent);

        assertCommandFailure(matchCommandStudent, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyListStudent_throwsCommandException() {
        Index outOfBoundIndexStudent = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        MatchCommand matchCommandStudent = new MatchCommand(outOfBoundIndexStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_NO_PERSON);

        assertCommandFailure(matchCommandStudent, model, String.format(Messages.MESSAGE_EMPTY_LIST,
                PersonType.STUDENT));
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

        // different command -> returns false
        assertFalse(standardCommand.equals(new DeleteCommand(INDEX_SECOND_PERSON, PersonType.STUDENT)));
    }

    private List<Tag> getStudentTagList(Student student) {
        Set<Tag> studentTag = student.getTags();
        return new ArrayList<>(studentTag);
    }
}
