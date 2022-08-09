package de.xenadu.fsApi.pojos;

import de.xenadu.fsApi.asserts.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class ByteArrayFileContent implements FileContent {

    private String filename;
    private Path pathToFile;
    private boolean overwriteExisting = false;

    private File file;
    private byte[] content;
    private Charset charset = StandardCharsets.UTF_8;

    public ByteArrayFileContent(File file) {
        this.file = file;
        this.filename = file.getName();

        setPathToFile(file.toPath());
    }

    private void setPathToFile(Path path) {
        Path parent = path.getParent();
        if (parent != null) {
            this.pathToFile = path.getParent();
        } else {
            this.pathToFile = path;
        }
    }

    public ByteArrayFileContent(byte[] bytes) {
        this.content = bytes;
    }

    public ByteArrayFileContent(List<String> fileContent, Charset cs, String delimiter) {
        String strContent = String.join(delimiter, fileContent);
        this.content = strContent.getBytes(cs);
    }

    public ByteArrayFileContent(List<String> fileContent, Charset cs) {
        this(fileContent, cs, "\n");
    }

    public ByteArrayFileContent(String fileContent, Charset charset) {
        this.charset = charset;
        this.content = fileContent.getBytes(charset);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public File readFile() {
        if (file == null) {
            throw new IllegalStateException("Transient FileCommand");
        }

        return file;
    }

    @Override
    public File writeToFile(Path absolutePath) {
        Assert.fieldNotNull(content, "Call writeToFile(Path absolutePath) without content");
        final File f = absolutePath.toFile();

        boolean created = false;
        if (f.exists()) {
            created = true;
        }

        if (!created) {
            created = createNewFile(f);
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
    public File writeToFile(Path pathToFile, String filename) {
        return writeToFile(Path.of(pathToFile.toString() + "/" + filename));
    }

    private boolean createNewFile(File f) {
        try {
            return f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
