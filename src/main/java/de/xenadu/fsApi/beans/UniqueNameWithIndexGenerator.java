package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;

import java.io.File;
import java.nio.file.Path;

public class UniqueNameWithIndexGenerator implements UniqueFilenameGenerator {

    @Override
    public String generateUniqueName(Path absolutePath, String nameProposal) {
        Assert.pathsExists(absolutePath);
        Assert.pathIsDirectory(absolutePath);

        File f = new File(absolutePath.toAbsolutePath() + "/" + nameProposal);
        int i = 0;

        while (f.exists()) {
            i++;
            f = new File(absolutePath.toAbsolutePath() + "/" + i + "_" + nameProposal);
        }

        return f.toPath().toString();
    }
}
