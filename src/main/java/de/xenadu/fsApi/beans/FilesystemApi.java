package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.pojos.FileCommand;
import de.xenadu.fsApi.pojos.DownloadFileResponse;

import java.nio.file.Path;

public interface FilesystemApi {

    void deleteAllFiles(Path path);
    void saveFile(FileCommand fileCommand);

    DownloadFileResponse getFileByPathNameAndFileName(String pathName, String filename);
    // moveFile
    // copyFile (with CopyOtions)

    class FilesystemApiException extends RuntimeException {
        public FilesystemApiException() {
        }

        public FilesystemApiException(String message) {
            super(message);
        }
    }

}
