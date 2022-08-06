package de.xenadu.fsApi.beans;

import java.nio.file.Path;

public interface UniqueFilenameGenerator {

    String generateUniqueName(Path absolutePath, String nameProposal);

}
