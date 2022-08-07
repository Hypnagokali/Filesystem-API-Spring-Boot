package de.xenadu.fsApi.types;

public interface FsFile {

    void populate(String fullPath, String originalName, long size);
    String getFullPath();
    String getFilename();
    String getOriginalName();
    long getSize();

}
