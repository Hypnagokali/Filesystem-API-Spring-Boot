package de.xenadu.fsApi.types;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FilenameTest {

    @Test
    public void emptyStringTest() throws Exception {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Filename(""));
    }

    @Test
    public void withoutExtensionTest() throws Exception {
        final Filename filename = new Filename("some_files");

        assertThat(filename.getExtension()).isEqualTo("");
        assertThat(filename.getName()).isEqualTo("some_files");
    }

    @Test
    public void withExtensionTest() throws Exception {
        final Filename filename = new Filename("some.files.txt");

        assertThat(filename.getExtension()).isEqualTo(".txt");
        assertThat(filename.getName()).isEqualTo("some.files");
    }
}