package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;
import de.xenadu.fsApi.pojos.DownloadFileResponse;
import de.xenadu.fsApi.pojos.FileCommand;
import de.xenadu.fsApi.pojos.RawByteDownloadFileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFileSystemApi implements FilesystemApi {

    private final PathWrapper pathWrapper;

    @Override
    public void deleteAllFiles(Path path) {
        Assert.pathsExists(path);
        Assert.pathIsDirectory(path);

        final File[] files = path.toFile().listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveFile(FileCommand fileCommand) {

    }

    @Override
    public DownloadFileResponse getFileByPathNameAndFileName(String pathName, String filename) {
        final Path pathByName = pathWrapper.getPathByName(pathName)
                .orElseThrow(() -> new FilesystemApiException("Path with name '" + pathName + "' not found!"));

        final File file = getFileFromPath(pathByName + "/" + filename);

        DownloadFileResponse response = new RawByteDownloadFileResponse(file);

        return null;
    }

    private File getFileFromPath(String fullPathToFile) {
        File file = new File(fullPathToFile);
        if (!file.exists()) {
            throw new FilesystemApiException("File does not exist: " + file.getAbsolutePath());
        }

        return file;
    }
}
