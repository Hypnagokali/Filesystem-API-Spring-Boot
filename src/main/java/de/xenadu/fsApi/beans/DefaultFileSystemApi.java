package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;
import de.xenadu.fsApi.pojos.DownloadFileResponse;
import de.xenadu.fsApi.pojos.FileCommand;
import de.xenadu.fsApi.pojos.RawByteDownloadFileResponse;
import de.xenadu.fsApi.types.FsFile;
import de.xenadu.fsApi.types.FsFileImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public <T extends FsFile> T uploadFileTyped(InputStream fileContent, String originalName, String pathToFile, String filename, T upload) {
        final FsFile fsFile = uploadFile(fileContent, originalName, pathToFile, filename);
        upload.populate(fsFile.getFullPath(), fsFile.getOriginalName(), fsFile.getSize());

        return upload;
    }

    @Override
    public <T extends FsFile> T uploadFileTyped(MultipartFile file, String pathToFile, String filename, T upload) {
        final FsFile fsFile = uploadFile(file, pathToFile, filename);
        upload.populate(fsFile.getFullPath(), fsFile.getOriginalName(), fsFile.getSize());

        return upload;
    }

    @Override
    public FsFile uploadFile(InputStream fileContent, String originalName, String pathToFile, String filename) {
        File fsFile = new File(pathToFile + "/" + filename);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fsFile);
        } catch (FileNotFoundException e) {
            throw new FilesystemApiException("Could not open FileOutputStream", e);
        }

        try {
            fileContent.transferTo(fos);
        } catch (IOException e) {
            throw new FilesystemApiException("Could not transfer to filesystem", e);
        }

        final long size;
        try {
            size = Files.size(fsFile.toPath());
        } catch (IOException e) {
            throw new FilesystemApiException("Could not get size from file", e);
        }


        return new FsFileImpl(fsFile.getAbsolutePath(), originalName, size);
    }

    @Override
    public FsFile uploadFile(MultipartFile file, String pathToFile, String filename) {
        final File fsFile = new File(pathToFile + "/" + filename);
        try {
            file.transferTo(fsFile);
        } catch (IOException e) {
            throw new FilesystemApiException("Could not transfer to filesystem", e);
        }

        return new FsFileImpl(fsFile.getAbsolutePath(), file.getOriginalFilename(), file.getSize());
    }

    @Override
    public DownloadFileResponse getFileByAbsolutePath(String absolutePath) {
        final File file = getFileFromPath(absolutePath);

        return new RawByteDownloadFileResponse(file);
    }

    @Override
    public DownloadFileResponse getFileByPathNameAndFileName(String pathName, String filename) {
        final Path pathByName = pathWrapper.getPathByName(pathName)
                .orElseThrow(() -> new FilesystemApiException("Path with name '" + pathName + "' not found!"));

        final File file = getFileFromPath(pathByName + "/" + filename);

        return new RawByteDownloadFileResponse(file);
    }

    private File getFileFromPath(String fullPathToFile) {
        File file = new File(fullPathToFile);
        if (!file.exists()) {
            throw new FilesystemApiException("File does not exist: " + file.getAbsolutePath());
        }

        return file;
    }
}
