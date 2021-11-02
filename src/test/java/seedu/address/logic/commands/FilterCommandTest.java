package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ROXANNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_GRADUATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUALIFICATION_MOE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutorsForFilterTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.QualificationContainsQualificationPredicate;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalCliTutorsForFilterTest(), new UserPrefs());

    @Test
    public void execute_validFilterCommandGender_success() {
        // Match the first student in the list
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getCliTutors(), new UserPrefs());
        List<Tag> ls = new ArrayList<>();
        ls.addAll(studentToMatch.getTags());
        expectedModel.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        model.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        GenderContainsGenderPredicate predicate = prepareGenderPredicate(VALID_GENDER_AMY);
        expectedModel.filterMatchedTutor(predicate);

        FilterCommand filterCommand = new FilterCommand(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_TUTORS_LISTED_OVERVIEW, 1);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterCommandName_success() {
        // Match the first student in the list
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getCliTutors(), new UserPrefs());
        List<Tag> ls = new ArrayList<>();
        ls.addAll(studentToMatch.getTags());
        expectedModel.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        model.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        NameContainsKeywordsPredicate predicate = prepareNamePredicate(VALID_NAME_ROXANNE);
        expectedModel.filterMatchedTutor(predicate);

        FilterCommand filterCommand = new FilterCommand(predicate);

        String expectedMessage = String.format(String.format(Messages.MESSAGE_TUTORS_LISTED_OVERVIEW, 1));

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterCommandQualification_success() {
        // Match the first student in the list
        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getCliTutors(), new UserPrefs());
        List<Tag> ls = new ArrayList<>();
        ls.addAll(studentToMatch.getTags());
        expectedModel.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        model.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);

        QualificationContainsQualificationPredicate predicate = prepareQualificationPredicate(VALID_QUALIFICATION_MOE);
        expectedModel.filterMatchedTutor(predicate);

        FilterCommand filterCommand = new FilterCommand(predicate);

        String expectedMessage = String.format(String.format(Messages.MESSAGE_TUTORS_LISTED_OVERVIEW, 1));

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyTutorList_throwsCommandException() {
        Predicate<Person> predicate = prepareGenderPredicate(VALID_GENDER_AMY);

        ModelManager expectedModel = new ModelManager(model.getCliTutors(), new UserPrefs());
        expectedModel.filterMatchedTutor(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_FAILED);

        FilterCommand filterCommand = new FilterCommand(predicate);

        assertCommandFailure(filterCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
        Gender gender = new Gender(VALID_GENDER_AMY);
        Qualification qualification = new Qualification(VALID_QUALIFICATION_GRADUATE);
        builder.setGender(gender);
        builder.setQualification(qualification);
        Predicate<Person> predicate = builder.build();
        final FilterCommand standardCommand = new FilterCommand(predicate);

        // same values -> returns true
        FilterCommand commandWithSameValues = new FilterCommand(predicate);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    private List<Tag> getStudentTagList(Student student) {
        Set<Tag> studentTag = student.getTags();
        ArrayList<Tag> ls = new ArrayList<>();
        studentTag.stream().forEach(tag -> ls.add(tag));
        return ls;
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsNamePredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code GenderContainsGenderPredicate}.
     */
    private GenderContainsGenderPredicate prepareGenderPredicate(String userInput) {
        return new GenderContainsGenderPredicate(Arrays.asList(new Gender(userInput)));
    }

    /**
     * Parses {@code userInput} into a {@code GenderContainsGenderPredicate}.
     */
    private QualificationContainsQualificationPredicate prepareQualificationPredicate(String userInput) {
        return new QualificationContainsQualificationPredicate(Arrays.asList(new Qualification(userInput)));
    }
}
