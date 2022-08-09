package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.pojos.DownloadFileResponse;
import de.xenadu.fsApi.types.FsFile;
import de.xenadu.fsApi.types.FsPopulator;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

public interface FilesystemApi {

    void deleteAllFiles(Path path);
    void saveFileContent(String fileContent, Charset cs, Path absolutePath);
    void saveFileContent(List<String> fileContent, Charset cs, Path absolutePath);
    // moveFile
    // copyFile (with CopyOptions)

    <T extends FsPopulator> T uploadFileTyped(InputStream fileContent, String originalName, String pathToFile, String filename, T upload);
    <T extends FsPopulator> T uploadFileTyped(MultipartFile file, String pathToFile, String filename, T upload);

    FsFile uploadFile(InputStream fileContent, String originalName, String pathToFile, String filename);
    FsFile uploadFile(MultipartFile file, String pathToFile, String filename);

    DownloadFileResponse getFileByAbsolutePath(String absolutePath);
    DownloadFileResponse getFileByPathNameAndFileName(String pathName, String filename);

    List<String> readAllLines(String absolutePath, Charset cs);

    String readString(String absolutePath, Charset cs);

    FsFile backupFile(String pathToFile, String backupPath);


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
