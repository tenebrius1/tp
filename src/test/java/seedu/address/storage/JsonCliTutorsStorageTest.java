package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalCliTutors;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CliTutors;
import seedu.address.model.ReadOnlyCliTutors;

public class JsonCliTutorsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCliTutorsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCliTutors_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCliTutors(null));
    }

    private java.util.Optional<ReadOnlyCliTutors> readCliTutors(String filePath) throws Exception {
        return new JsonCliTutorsStorage(Paths.get(filePath)).readCliTutors(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCliTutors("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCliTutors("notJsonFormatCliTutors.json"));
    }

    @Test
    public void readCliTutors_invalidPersonCliTutors_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCliTutors("invalidPersonCliTutors.json"));
    }

    @Test
    public void readCliTutors_invalidAndValidPersonCliTutors_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCliTutors("invalidAndValidPersonCliTutors.json"));
    }

    @Test
    public void readAndSaveCliTutors_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCliTutors.json");
        CliTutors original = getTypicalCliTutors();
        JsonCliTutorsStorage jsonCliTutorsStorage = new JsonCliTutorsStorage(filePath);

        // Save in new file and read back
        jsonCliTutorsStorage.saveCliTutors(original, filePath);
        ReadOnlyCliTutors readBack = jsonCliTutorsStorage.readCliTutors(filePath).get();
        assertEquals(original, new CliTutors(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTutor(HOON);
        original.removeTutor(ALICE);
        jsonCliTutorsStorage.saveCliTutors(original, filePath);
        readBack = jsonCliTutorsStorage.readCliTutors(filePath).get();
        assertEquals(original, new CliTutors(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonCliTutorsStorage.saveCliTutors(original); // file path not specified
        readBack = jsonCliTutorsStorage.readCliTutors().get(); // file path not specified
        assertEquals(original, new CliTutors(readBack));
    }

    @Test
    public void saveCliTutors_nullCliTutors_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCliTutors(null, "SomeFile.json"));
    }

    /**
     * Saves {@code cliTutors} at the specified {@code filePath}.
     */
    private void saveCliTutors(ReadOnlyCliTutors cliTutors, String filePath) {
        try {
            new JsonCliTutorsStorage(Paths.get(filePath))
                    .saveCliTutors(cliTutors, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCliTutors_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCliTutors(new CliTutors(), null));
    }
}
