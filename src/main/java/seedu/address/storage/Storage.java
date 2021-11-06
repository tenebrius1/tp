package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCliTutors;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CliTutorsStorage, UserPrefsStorage {
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCliTutorsFilePath();

    @Override
    Optional<ReadOnlyCliTutors> readCliTutors() throws DataConversionException, IOException;

    @Override
    void saveCliTutors(ReadOnlyCliTutors cliTutors) throws IOException;
}
