package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class DefaultPathWrapper implements PathWrapper {

    private final Map<String, String> pathMap;

    public DefaultPathWrapper(Map<String, String> pathMap) {
        this.pathMap = pathMap;
    }

    @Override
    public void checkPaths() {
        for (String path : pathMap.values()) {
            Assert.pathsExists(Path.of(path));
        }
    }

    @Override
    public Optional<Path> getPathByName(String pathByName) {
        return pathMap.get(pathByName) == null ? Optional.empty() : Optional.of(Path.of(pathMap.get(pathByName)));
    }

    @Override
    public void addPath(String pathName, String absolutePath) {
        Assert.pathsExists(Path.of(absolutePath));
        Assert.pathIsDirectory(Path.of(absolutePath));

        pathMap.put(pathName, absolutePath);
    }
}
