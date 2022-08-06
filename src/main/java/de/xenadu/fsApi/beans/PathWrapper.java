package de.xenadu.fsApi.beans;

import java.nio.file.Path;
import java.util.Optional;

public interface PathWrapper {

    void checkPaths();
    Optional<Path> getPathByName(String pathByName);
    void addPath(String pathName, String absolutePath);

}
