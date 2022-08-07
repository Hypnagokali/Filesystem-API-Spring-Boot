package de.xenadu.fsApi.beans;

import de.xenadu.fsApi.asserts.Assert;
import de.xenadu.fsApi.types.Filename;

import java.io.File;
import java.nio.file.Path;

public class UniqueNameWithIndexGenerator implements UniqueFilenameGenerator {

    @Override
    public synchronized String generateUniqueName(Path absolutePath, String nameProposal) {
        Assert.pathsExists(absolutePath);
        Assert.pathIsDirectory(absolutePath);

        File f = new File(absolutePath.toAbsolutePath() + "/" + nameProposal);
        int i = 0;

        while (f.exists()) {
            i++;
            Filename filename = new Filename(nameProposal);
            filename.addSuffix("_" + i);
            f = new File(absolutePath.toAbsolutePath() + "/" + filename.getFullName());
        }

        return f.getName();
    }
}
