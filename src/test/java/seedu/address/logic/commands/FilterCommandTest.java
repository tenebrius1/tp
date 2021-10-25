package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookForFilterTest(), new UserPrefs());

//    @Test
//    public void execute_normalFilterCommand_success() {
//        Student studentToMatch = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
//        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//
//        String expectedMessage = String.format(MESSAGE_TUTORS_LISTED_OVERVIEW, 1);
//
//        GenderContainsGenderPredicate predicate = preparePredicate("f");
//        FilterCommand command = new FilterCommand(predicate);
//
//        List<Tag> ls = new ArrayList<>();
//        ls.addAll(studentToMatch.getTags());
//
//        expectedModel.updateMatchedTutor(new TagsContainTagPredicate(getStudentTagList(studentToMatch)), ls);
//        expectedModel.filterMatchedTutor(predicate);
//
//        assertCommandSuccess(command, model, expectedMessage, expectedModel);
//    }

    /**
     * Parses {@code userInput} into a {@code GenderContainsGenderPredicate}.
     */
    private GenderContainsGenderPredicate preparePredicate(String userInput) {
        return new GenderContainsGenderPredicate(Arrays.asList(new Gender(userInput)));
    }

    private List<Tag> getStudentTagList(Student student) {
        Set<Tag> studentTag = student.getTags();
        ArrayList<Tag> ls = new ArrayList<>();
        studentTag.stream().forEach(tag -> ls.add(tag));
        return ls;
    }

    @Test
    public void equals() {
        ChainedPredicate.Builder builder = new ChainedPredicate.Builder();
        Gender TEST_GENDER = new Gender("F");
        Qualification TEST_QUALIFICATION = new Qualification("2");
        builder.setGender(TEST_GENDER);
        builder.setQualification(TEST_QUALIFICATION);
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
}
