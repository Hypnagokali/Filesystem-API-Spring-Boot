package de.xenadu.fsApi.pojos;

import java.io.File;
import java.nio.file.Path;

/**
 * FileCommand
 *
 * Abstraction of file content, with or without a path and filename.
 * It could be just a String or any other content, and if it has no filename, than this FileCommand is transient. It has not been saved yet.
 *
 */
public interface FileCommand {

    /**
     * throws IllegalStateException if FileCommand is transient
     * @return File
     */
    File toFile();

    /**
     *
     * @param pathToFile the path to the directory, where the file will be saved
     * @param filename the name of the file
     * @return File
     */
    File toFile(Path pathToFile, String filename);

    /**
     *
     * @return true, if file has not been saved yet
     */
    boolean isTransient();

    /**
     *
     * @return true, if this command overwrites an existing file with same name and path
     */
    boolean isOverwriteExistingFile();

    /**
     *
     * @param overwrite sets the overwrite option
     */
    void setOverwriteExistingFile(boolean overwrite);

    String getFilename();

    Path getPathToFile();
}
