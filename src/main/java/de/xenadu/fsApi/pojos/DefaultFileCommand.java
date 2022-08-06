package de.xenadu.fsApi.pojos;

import de.xenadu.fsApi.asserts.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class DefaultFileCommand implements FileCommand {

    private String filename;
    private Path pathToFile;
    private boolean overwriteExisting = false;

    private File file;
    private byte[] content;
    private final boolean isOnlyBytes;
    private Charset charset = StandardCharsets.UTF_8;

    public DefaultFileCommand(File file) {
        this.file = file;
        this.filename = file.getName();
        this.pathToFile = file.getParentFile().toPath();
        this.isOnlyBytes = true;
    }

    public DefaultFileCommand(byte[] bytes) {
        this.content = bytes;
        this.isOnlyBytes = true;
    }

    public DefaultFileCommand(String fileContent, Charset charset) {
        this.charset = charset;
        this.content = fileContent.getBytes(charset);
        this.isOnlyBytes = false;
    }

    @Override
    public File toFile() {
        if (file == null) {
            throw new IllegalStateException("Transient FileCommand");
        }

        return file;
    }

    @Override
    public File toFile(Path pathToFile, String filename) {
        File f = new File(pathToFile + "/" + filename);

        if (f.exists()) {
            throw new IllegalStateException("Datei existiert bereits: " + f.getAbsolutePath());
        }

        boolean created = false;
        try {
            created = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!created) {
            throw new IllegalStateException("Datei konnte nicht erzeugt werden: " + f.getAbsolutePath());
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Assert.notNull(out);
        try {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    @Override
    public boolean isTransient() {
        return filename == null || filename.isEmpty();
    }

    @Override
    public boolean isOverwriteExistingFile() {
        return overwriteExisting;
    }

    @Override
    public void setOverwriteExistingFile(boolean overwrite) {
        this.overwriteExisting = overwrite;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public Path getPathToFile() {
        return pathToFile;
    }
}
