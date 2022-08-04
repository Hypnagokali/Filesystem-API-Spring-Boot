package de.xenadu.fsApi.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultPathWrapper implements PathWrapper {

    private Map<String, String> pathMap = new HashMap<>();

    public DefaultPathWrapper(Map<String, String> pathMap) {
        this.pathMap = pathMap;
    }

    @Override
    public Optional<String> getPathByName(String pathByName) {
        return pathMap.get(pathByName) == null ? Optional.empty() : Optional.of(pathMap.get(pathByName));
    }
}
