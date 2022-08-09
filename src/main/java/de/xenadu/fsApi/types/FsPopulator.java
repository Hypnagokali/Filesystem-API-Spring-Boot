package de.xenadu.fsApi.types;

public interface FsPopulator {

    void populate(String fullPath, String originalName, long size);

}
