package de.xenadu.fsApi.types;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FsFileImplTest {

    @Test
    public void whenBasePathIsRoot_ExpectConstructorToWork() throws Exception {
        FsFile fsFile = new FsFileImpl("rabbit.csv", "foo.csv", 123);

        assertThat(fsFile.getFilename()).isEqualTo("rabbit.csv");
        assertThat(fsFile.getOriginalName()).isEqualTo("foo.csv");
    }
}