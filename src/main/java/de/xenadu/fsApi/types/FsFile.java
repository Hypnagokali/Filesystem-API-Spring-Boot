package de.xenadu.fsApi.types;

public interface FsFile {

    String getFullPath();
    String getFilename();
    String getOriginalName();
    long getSize();

}
