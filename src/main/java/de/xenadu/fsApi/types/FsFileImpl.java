package de.xenadu.fsApi.types;

import java.nio.file.Path;

public class FsFileImpl implements FsFile {

    private String fullPath;
    private String filename;
    private String originalFilename;
    private long size;

    public FsFileImpl(String fullPath, String originalFilename, long size) {
        setFilename(fullPath);

        this.originalFilename = originalFilename;
        this.size = size;
        this.fullPath = fullPath;
    }

    private void setFilename(String fullPath) {
        final Path parent = Path.of(fullPath).getParent();

        if (parent == null) {
            this.filename = fullPath;
        } else {
            this.filename = parent.toString();
        }
    }

    @Override
    public void populate(String fullPath, String originalName, long size) {
        setFilename(fullPath);
        this.originalFilename = originalName;
        this.size = size;
        this.fullPath = fullPath;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getOriginalName() {
        return originalFilename;
    }

    @Override
    public long getSize() {
        return size;
    }
}
