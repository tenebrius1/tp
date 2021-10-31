package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCliTutors;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CliTutors data in local storage.
 */
public class StorageManager implements Storage {
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CliTutorsStorage cliTutorsStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code CliTutorsStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(CliTutorsStorage cliTutorsStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cliTutorsStorage = cliTutorsStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ CliTutors methods ==============================
    @Override
    public Path getCliTutorsFilePath() {
        return cliTutorsStorage.getCliTutorsFilePath();
    }

    @Override
    public Optional<ReadOnlyCliTutors> readCliTutors() throws DataConversionException, IOException {
        return readCliTutors(cliTutorsStorage.getCliTutorsFilePath());
    }

    @Override
    public Optional<ReadOnlyCliTutors> readCliTutors(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cliTutorsStorage.readCliTutors(filePath);
    }

    @Override
    public void saveCliTutors(ReadOnlyCliTutors cliTutors) throws IOException {
        saveCliTutors(cliTutors, cliTutorsStorage.getCliTutorsFilePath());
    }

    @Override
    public void saveCliTutors(ReadOnlyCliTutors cliTutors, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cliTutorsStorage.saveCliTutors(cliTutors, filePath);
    }
}
