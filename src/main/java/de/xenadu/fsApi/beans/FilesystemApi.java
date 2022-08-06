package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.pojos.FileCommand;
import de.xenadu.fsApi.pojos.DownloadFileResponse;
import de.xenadu.fsApi.types.FsFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FilesystemApi {

    void deleteAllFiles(Path path);
    void saveFile(FileCommand fileCommand);
    FsFile uploadFile(MultipartFile file, String pathToFile, String filename);

    DownloadFileResponse getFileByPathNameAndFileName(String pathName, String filename);
    // moveFile
    // copyFile (with CopyOtions)

    class FilesystemApiException extends RuntimeException {
        public FilesystemApiException() {
        }

        public FilesystemApiException(String message) {
            super(message);
        }

        public FilesystemApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
