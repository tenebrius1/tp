package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LETTER_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.LETTER_DESC_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUALIFICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PM_TP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TP;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PersonType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCliTutors;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.storage.JsonCliTutorsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TutorBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCliTutorsStorage cliTutorsStorage =
                new JsonCliTutorsStorage(temporaryFolder.resolve("cliTutors.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(cliTutorsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete s 9";
        assertCommandException(deleteCommand, String.format(Messages.MESSAGE_EMPTY_LIST, PersonType.STUDENT));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand + LETTER_DESC_TUTOR, ListCommand.MESSAGE_EMPTY_LIST, model);
        assertCommandSuccess(listCommand + LETTER_DESC_STUDENT, ListCommand.MESSAGE_EMPTY_LIST, model);
        model.addTutor(ALICE);
        model.addStudent(DANIEL);
        assertCommandSuccess(listCommand + LETTER_DESC_TUTOR, ListCommand.MESSAGE_SUCCESS_TUTOR, model);
        assertCommandSuccess(listCommand + LETTER_DESC_STUDENT, ListCommand.MESSAGE_SUCCESS_STUDENT,
                model);
    }

    @Test
    public void execute_storageThrowsIoExceptionTutor_throwsCommandException() {
        // Setup LogicManager with JsonCliTutorsIoExceptionThrowingStub
        JsonCliTutorsStorage cliTutorsStorage =
                new JsonCliTutorsIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCliTutors.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(cliTutorsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + LETTER_DESC_TUTOR + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + QUALIFICATION_DESC_BOB + TAG_DESC_PM_TP;
        Tutor expectedTutor = new TutorBuilder().withTags(VALID_TAG_PM, VALID_TAG_TP).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTutor(expectedTutor);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void execute_storageThrowsIoExceptionStudent_throwsCommandException() {
        // Setup LogicManager with JsonCliTutorsIoExceptionThrowingStub
        JsonCliTutorsStorage cliTutorsStorage =
                new JsonCliTutorsIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCliTutors.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(cliTutorsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + LETTER_DESC_STUDENT + NAME_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_PM;
        Student expectedStudent = new StudentBuilder().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addStudent(expectedStudent);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTutorList_success() {
        assertEquals(logic.getFilteredTutorList(), new ModelManager().getFilteredTutorList());
    }

    @Test
    public void getFilteredStudentList_success() {
        assertEquals(logic.getFilteredStudentList(), new ModelManager().getFilteredTutorList());
    }

    @Test
    public void getMatchedTutorList_success() {
        assertEquals(logic.getMatchedTutorList(), new ModelManager().getFilteredTutorList());
    }

    @Test
    public void getFilteredTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTutorList().remove(0));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                logic.getFilteredStudentList().remove(INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void getMatchedTutorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                logic.getMatchedTutorList().remove(INDEX_FIRST_PERSON.getZeroBased()));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getCliTutors(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCliTutorsIoExceptionThrowingStub extends JsonCliTutorsStorage {
        private JsonCliTutorsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCliTutors(ReadOnlyCliTutors cliTutors, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
