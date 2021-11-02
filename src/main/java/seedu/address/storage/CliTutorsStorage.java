package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCliTutors;

/**
 * Represents a storage for {@link seedu.address.model.CliTutors}.
 */
public interface CliTutorsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCliTutorsFilePath();

    /**
     * Returns CliTutors data as a {@link ReadOnlyCliTutors}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCliTutors> readCliTutors() throws DataConversionException, IOException;

    /**
     * @see #getCliTutorsFilePath()
     */
    Optional<ReadOnlyCliTutors> readCliTutors(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCliTutors} to the storage.
     * @param cliTutors cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCliTutors(ReadOnlyCliTutors cliTutors) throws IOException;

    /**
     * @see #saveCliTutors(ReadOnlyCliTutors)
     */
    void saveCliTutors(ReadOnlyCliTutors cliTutors, Path filePath) throws IOException;

}
