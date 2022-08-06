package de.xenadu.fsApi.asserts;

import java.nio.file.Path;

public final class Assert {

    public static void pathsExists(Path path) {
        if (!path.toFile().exists()) {
            throw new IllegalStateException("Path does not exist: '" + path + "'");
        }
    }

    public static void pathIsDirectory(Path path) {
        if (!path.toFile().isDirectory()) {
            throw new IllegalStateException("Path is not a directory: '" + path + "'");
        }
    }

    public static void notNull(Object o) {
        if (o == null) throw new IllegalArgumentException("Object must not be null");
    }
}
