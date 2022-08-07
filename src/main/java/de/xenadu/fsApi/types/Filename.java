package de.xenadu.fsApi.types;

import de.xenadu.fsApi.asserts.Assert;
import lombok.Getter;

@Getter
public class Filename {


    private String extension = "";
    private String name = "";

    public Filename(String filenameWithExtension) {
        Assert.notNullOrBlank(filenameWithExtension);
        this.extension = getExt(filenameWithExtension);
        this.name = getNameWithoutExt(filenameWithExtension, extension);
    }

    private String getNameWithoutExt(String filenameWithExtension, String extension) {
        if (extension.isBlank()) return filenameWithExtension;

        return filenameWithExtension.substring(0, filenameWithExtension.lastIndexOf(extension));
    }

    private String getExt(String filenameWithExtension) {
        final int i = filenameWithExtension.lastIndexOf('.');
        if (i >= 0) {
            return filenameWithExtension.substring(i);
        }

        return "";
    }

    public void addSuffix(String suffix) {
        this.name += suffix;
    }

    public void addPrefix(String prefix) {
        prefix += this.name;
    }

    public String getFullName() {
        return this.name + this.extension;
    }
}
