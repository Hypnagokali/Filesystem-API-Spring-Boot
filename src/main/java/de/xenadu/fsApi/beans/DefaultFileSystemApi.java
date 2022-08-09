package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;
import de.xenadu.fsApi.pojos.ByteArrayFileContent;
import de.xenadu.fsApi.pojos.DownloadFileResponse;
import de.xenadu.fsApi.pojos.FileContent;
import de.xenadu.fsApi.pojos.RawByteDownloadFileResponse;
import de.xenadu.fsApi.types.Filename;
import de.xenadu.fsApi.types.FsFile;
import de.xenadu.fsApi.types.FsFileImpl;
import de.xenadu.fsApi.types.FsPopulator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class DefaultFileSystemApi implements FilesystemApi {

    private final PathWrapper pathWrapper;
    private final UniqueFilenameGenerator uniqueFilenameGenerator;

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
    public void saveFileContent(String fileContent, Charset cs, Path absolutePath) {
        FileContent file = new ByteArrayFileContent(fileContent, cs);
        file.writeToFile(absolutePath);
    }

    @Override
    public void saveFileContent(List<String> fileContent, Charset cs, Path path) {
        FileContent file = new ByteArrayFileContent(fileContent, cs);
        file.writeToFile(path);
    }


    @Override
    public <T extends FsPopulator> T uploadFileTyped(InputStream fileContent, String originalName, String pathToFile, String filename, T upload) {
        final FsFile fsFile = uploadFile(fileContent, originalName, pathToFile, filename);
        upload.populate(fsFile.getFullPath(), fsFile.getOriginalName(), fsFile.getSize());

        return upload;
    }

    @Override
    public <T extends FsPopulator> T uploadFileTyped(MultipartFile file, String pathToFile, String filename, T upload) {
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

    @Override
    public List<String> readAllLines(String absolutePath, Charset cs) {
        Path path = Path.of(absolutePath);

        try {
            return Files.readAllLines(path, cs);
        } catch (IOException e) {
            throw new FilesystemApiException("Could not read file: " + path, e);
        }
    }

    @Override
    public String readString(String absolutePath, Charset cs) {
        final Path path = Path.of(absolutePath);

        try {
            return Files.readString(path, cs);
        } catch (IOException e) {
            throw new FilesystemApiException("Could not read file: " + path, e);
        }
    }

    @Override
    public FsFile backupFile(String pathToFile, String backupPath) {
        final File file = new File(pathToFile);

        final String name = uniqueFilenameGenerator.generateUniqueName(Path.of(backupPath), "_" + file.getName());
        final Path backupPathWithFile = Path.of(backupPath + "/" + name);

        try {
            Files.copy(file.toPath(), backupPathWithFile);
        } catch (IOException e) {
            throw new FilesystemApiException("Could not copy file", e);
        }

        FsFile fsFile = new FsFileImpl(backupPathWithFile.toString(), "", 0);


        return fsFile;
    }

    private File getFileFromPath(String fullPathToFile) {
        File file = new File(fullPathToFile);
        if (!file.exists()) {
            throw new FilesystemApiException("File does not exist: " + file.getAbsolutePath());
        }

        return file;
    }
}
