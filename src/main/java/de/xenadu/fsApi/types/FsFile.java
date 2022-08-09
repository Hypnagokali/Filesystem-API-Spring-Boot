package de.xenadu.fsApi.types;

public interface FsFile extends FsPopulator {

    String getFullPath();
    String getFilename();
    String getOriginalName();
    long getSize();

}
