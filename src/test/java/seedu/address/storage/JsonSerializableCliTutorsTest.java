package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CliTutors;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableCliTutorsTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCliTutorsTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsCliTutors.json");
    private static final Path INVALID_TUTOR_FILE = TEST_DATA_FOLDER.resolve("invalidTutorCliTutors.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentCliTutors.json");
    private static final Path DUPLICATE_TUTOR_FILE = TEST_DATA_FOLDER.resolve("duplicateTutorCliTutors.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentCliTutors.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCliTutors dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableCliTutors.class).get();
        CliTutors cliTutorsFromFile = dataFromFile.toModelType();
        CliTutors typicalPersonsCliTutors = TypicalPersons.getTypicalCliTutors();
        assertEquals(cliTutorsFromFile, typicalPersonsCliTutors);
    }

    @Test
    public void toModelType_invalidTutorFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCliTutors dataFromFile = JsonUtil.readJsonFile(INVALID_TUTOR_FILE,
                JsonSerializableCliTutors.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCliTutors dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableCliTutors.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTutors_throwsIllegalValueException() throws Exception {
        JsonSerializableCliTutors dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TUTOR_FILE,
                JsonSerializableCliTutors.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCliTutors.MESSAGE_DUPLICATE_TUTOR,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableCliTutors dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableCliTutors.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCliTutors.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }
}
