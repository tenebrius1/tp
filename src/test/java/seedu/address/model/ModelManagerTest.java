package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ENZIO;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Gender;
import seedu.address.model.person.GenderContainsGenderPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Qualification;
import seedu.address.model.person.QualificationContainsQualificationPredicate;
import seedu.address.model.person.Student;
import seedu.address.model.person.TagsContainTagPredicate;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CliTutorsBuilder;
import seedu.address.testutil.TypicalPersons;

public class ModelManagerTest {
    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CliTutors(), new CliTutors(modelManager.getCliTutors()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCliTutorsFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCliTutorsFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCliTutorsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCliTutorsFilePath(null));
    }

    @Test
    public void setCliTutorsFilePath_validPath_setsCliTutorsFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setCliTutorsFilePath(path);
        assertEquals(path, modelManager.getCliTutorsFilePath());
    }

    @Test
    public void hasTutor_nullTutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTutor(null));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasTutor_tutorNotInCliTutors_returnsFalse() {
        assertFalse(modelManager.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentNotInCliTutors_returnsFalse() {
        assertFalse(modelManager.hasStudent(DANIEL));
    }

    @Test
    public void hasTutor_tutorInCliTutors_returnsTrue() {
        modelManager.addTutor(ALICE);
        assertTrue(modelManager.hasTutor(ALICE));
    }

    @Test
    public void hasStudent_studentInCliTutors_returnsTrue() {
        modelManager.addStudent(DANIEL);
        assertTrue(modelManager.hasStudent(DANIEL));
    }

    @Test
    public void getFilteredTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTutorList().remove(
                INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(
                INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void getMatchedTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getMatchedTutorList().remove(
                INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void updateFilteredTutorList_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredTutorList(null));
    }

    @Test
    public void updateFilteredStudentList_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredStudentList(null));
    }

    @Test
    public void updateMatchedTutor_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateMatchedTutor(null, null,
                null));
    }

    @Test
    public void filterMatchedTutor_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.filterMatchedTutor(null));
    }

    @Test
    public void updateFilteredTutorList_modifyList_success() {
        ArrayList<String> ls = new ArrayList<>();
        ls.add("Alice");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        CliTutors cliTutors = new CliTutors();
        cliTutors.addTutor(ALICE);
        FilteredList<Tutor> expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        modelManager.updateFilteredTutorList(predicate);
        assertEquals(expectedTutorList, modelManager.getFilteredTutorList());
    }

    @Test
    public void updateFilteredStudentList_modifyList_success() {
        ArrayList<String> ls = new ArrayList<>();
        ls.add("Daniel");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(ls);
        TypicalPersons.getTypicalStudents().stream().forEach(student -> modelManager.addStudent(student));
        CliTutors cliTutors = new CliTutors();
        cliTutors.addStudent(DANIEL);
        FilteredList<Student> expectedStudentList = new FilteredList<>(cliTutors.getStudentList());
        modelManager.updateFilteredStudentList(predicate);
        assertEquals(expectedStudentList, modelManager.getFilteredStudentList());
    }

    @Test
    public void updateMatchedTutor_modifyList_success() {
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        CliTutors cliTutors = new CliTutors();
        cliTutors.addTutor(ALICE);
        FilteredList<Tutor> expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        modelManager.updateMatchedTutor(predicate, ls, DANIEL);
        assertEquals(expectedTutorList, modelManager.getMatchedTutorList());
        assertEquals(DANIEL, modelManager.getMatchedStudent());
    }

    @Test
    public void updateMatchedTutor_emptyMatchedList_success() {
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(GEORGE.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        CliTutors cliTutors = new CliTutors();
        FilteredList<Tutor> expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        modelManager.updateMatchedTutor(predicate, ls, GEORGE);
        assertEquals(expectedTutorList, modelManager.getMatchedTutorList());
        // Check matched student
        assertEquals(null, modelManager.getMatchedStudent());
    }

    @Test
    public void updateMatchedTutor_modifyList_throwAssertionError() {
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        assertThrows(AssertionError.class, () -> modelManager.updateMatchedTutor(predicate, new ArrayList<>(),
                DANIEL));
    }

    @Test
    public void clearMatchedTutorTest() {
        // Expected empty match list
        FilteredList<Tutor> expectedMatchList = new FilteredList<>(modelManager.getMatchedTutorList());

        // Clear empty list
        modelManager.clearMatchedTutor();
        assertEquals(expectedMatchList, modelManager.getMatchedTutorList());

        // Clear list after matching
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        // Valid match as TypicalTutors have tutors that teach subject that student DANIEL wants
        modelManager.updateMatchedTutor(predicate, ls, DANIEL);
        modelManager.clearMatchedTutor();
        assertEquals(expectedMatchList, modelManager.getMatchedTutorList());
    }

    @Test
    public void filterMatchedTutor_modifyList_success() {
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        modelManager.addTutor(ENZIO);
        CliTutors cliTutors = new CliTutors();
        cliTutors.addTutor(ENZIO);
        cliTutors.addTutor(ALICE);
        FilteredList<Tutor> expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        modelManager.updateMatchedTutor(predicate, ls, DANIEL);
        assertEquals(expectedTutorList, modelManager.getMatchedTutorList());
        // Check matched student
        assertEquals(DANIEL, modelManager.getMatchedStudent());

        // filter out females only
        ArrayList<Gender> genderList = new ArrayList<>();
        genderList.add(new Gender("F"));
        GenderContainsGenderPredicate predicate1 = new GenderContainsGenderPredicate(genderList);
        modelManager.filterMatchedTutor(predicate1);
        cliTutors.removeTutor(ENZIO);
        expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        assertEquals(expectedTutorList, modelManager.getMatchedTutorList());
        // Check matched student
        assertEquals(DANIEL, modelManager.getMatchedStudent());

        // filter out graduate qualification, no matches
        ArrayList<Qualification> qualificationList = new ArrayList<>();
        genderList.add(new Gender("F"));
        QualificationContainsQualificationPredicate predicate2 = new QualificationContainsQualificationPredicate(
                qualificationList);
        modelManager.filterMatchedTutor(predicate2);
        cliTutors.removeTutor(ALICE);
        expectedTutorList = new FilteredList<>(cliTutors.getTutorList());
        assertEquals(expectedTutorList, modelManager.getMatchedTutorList());
        // Check matched student
        assertEquals(null, modelManager.getMatchedStudent());
    }

    @Test
    public void getMatchedStudentTest() {
        Student expectedStudent = null;

        // Initial student; first start up
        assertEquals(expectedStudent, modelManager.getMatchedStudent());

        // After successful match
        expectedStudent = DANIEL;
        ArrayList<Tag> ls = new ArrayList<>();
        ls.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(ls);
        TypicalPersons.getTypicalTutors().stream().forEach(tutor -> modelManager.addTutor(tutor));
        // Valid match as TypicalTutors have tutors that teach subject that student DANIEL wants
        modelManager.updateMatchedTutor(predicate, ls, DANIEL);
        assertEquals(expectedStudent, modelManager.getMatchedStudent());

        // After successful filtering match list
        ArrayList<Gender> genderList = new ArrayList<>();
        genderList.add(new Gender("F"));
        GenderContainsGenderPredicate predicate1 = new GenderContainsGenderPredicate(genderList);
        modelManager.filterMatchedTutor(predicate1);
        assertEquals(expectedStudent, modelManager.getMatchedStudent());

        // After filtering to 0 matches
        expectedStudent = null;
        ArrayList<Qualification> qualificationList = new ArrayList<>();
        genderList.add(new Gender("F"));
        QualificationContainsQualificationPredicate predicate2 = new QualificationContainsQualificationPredicate(
                qualificationList);
        modelManager.filterMatchedTutor(predicate2);
        assertEquals(expectedStudent, modelManager.getMatchedStudent());

        // After clearing
        modelManager.clearMatchedTutor();
        assertEquals(expectedStudent, modelManager.getMatchedStudent());
    }



    @Test
    public void equals() {
        CliTutors cliTutors = new CliTutorsBuilder().withTutor(ALICE).withStudent(DANIEL).build();
        CliTutors differentCliTutors = new CliTutors();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(cliTutors, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(cliTutors, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different cliTutors -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCliTutors, userPrefs)));

        // different filteredList (tutor) -> returns false
        String[] keywords = BENSON.getName().fullName.split("\\s+");
        modelManager.updateFilteredTutorList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(cliTutors, userPrefs)));

        // different filteredList (student) -> returns false
        keywords = IDA.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(cliTutors, userPrefs)));

        // different match list
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.addAll(DANIEL.getTags());
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(tagList);
        modelManager.updateMatchedTutor(predicate, tagList, DANIEL);
        assertFalse(modelManager.equals(new ModelManager(cliTutors, userPrefs)));
        // different matchStudent after above match
        assertFalse(modelManager.equals(new ModelManager(cliTutors, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        modelManager.clearMatchedTutor();

        Set<Tag> studentTag = DANIEL.getTags();
        List<Tag> ls = new ArrayList<>();
        studentTag.stream().forEach(tag -> ls.add(tag));
        modelManager.updateMatchedTutor(new TagsContainTagPredicate(ls), ls, DANIEL);
        assertNotEquals(modelManager, new ModelManager(cliTutors, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        modelManager.clearMatchedTutor();

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCliTutorsFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(cliTutors, differentUserPrefs)));
    }
}
