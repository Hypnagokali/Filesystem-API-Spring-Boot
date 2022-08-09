package de.xenadu.fsApi.pojos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayFileCommandTest {


    @AfterEach
    public void tearDown() throws Exception {
        final URL resource = getClass().getResource("/testDir/anotherfile.txt");

        if (resource != null) {
            final File file = new File(resource.toURI());
            file.delete();
        }
    }

    @Test
    public void whenCreatedFromString_AndSavingItToFile_ExpectThatFileHasInputContent() throws Exception {
        final URL resource = getClass().getResource("/testDir");
        final URI uri = resource.toURI();
        final Path pathToFile = new File(uri).toPath();


        FileContent command = new ByteArrayFileContent("Some content", StandardCharsets.UTF_8);
        final File file = command.writeToFile(pathToFile, "anotherfile.txt");

        assertThat(file.exists()).isTrue();
    }

    @Test
    public void whenCreatedFromString_expectFileIsTransient() throws Exception {
        FileContent command = new ByteArrayFileContent("Some content", StandardCharsets.UTF_8);

        assertThat(command.isTransient()).isTrue();
    }

    @Test
    public void whenFileExists_expectNotTransient() throws Exception {
        final URL resource = getClass().getResource("/testDir/testfile.txt");
        final File file = new File(resource.toURI());
        FileContent command = new ByteArrayFileContent(file);

        assertThat(command.isTransient()).isFalse();
    }

    @Test
    public void whenFileExists_expectFilenameAndPath() throws Exception {
        final URL resource = getClass().getResource("/testDir/testfile.txt");
        final File file = new File(resource.toURI());
        FileContent command = new ByteArrayFileContent(file);

        assertThat(command.getFilename()).isEqualTo("testfile.txt");
        assertThat(command.getPathToFile().toString()).endsWith("/testDir");
    }
}