package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyCliTutors;

/**
 * A class to access CliTutors data stored as a json file on the hard disk.
 */
public class JsonCliTutorsStorage implements CliTutorsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCliTutorsStorage.class);

    private Path filePath;

    public JsonCliTutorsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCliTutorsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCliTutors> readCliTutors() throws DataConversionException {
        return readCliTutors(filePath);
    }

    /**
     * Similar to {@link #readCliTutors()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCliTutors> readCliTutors(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCliTutors> jsonCliTutors = JsonUtil.readJsonFile(
                filePath, JsonSerializableCliTutors.class);
        if (!jsonCliTutors.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCliTutors.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCliTutors(ReadOnlyCliTutors cliTutors) throws IOException {
        saveCliTutors(cliTutors, filePath);
    }

    /**
     * Similar to {@link #saveCliTutors(ReadOnlyCliTutors)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCliTutors(ReadOnlyCliTutors cliTutors, Path filePath) throws IOException {
        requireNonNull(cliTutors);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCliTutors(cliTutors), filePath);
    }

}
