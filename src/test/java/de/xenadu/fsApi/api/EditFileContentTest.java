package de.xenadu.fsApi.api;

import de.xenadu.fsApi.beans.FilesystemApi;
import de.xenadu.fsApi.types.FsFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EditFileContentTest {

    @Autowired
    FilesystemApi filesystemApi;

    File testFile;

    @BeforeEach
    public void setUp() throws Exception {
        final URI uri = Objects.requireNonNull(getClass().getResource("/testDir/some.csv")).toURI();
        final URI uriPath = Objects.requireNonNull(getClass().getResource("/testDir")).toURI();
        FsFile backup = filesystemApi.backupFile(uri.getPath(), uriPath.getPath());

        testFile = new File(backup.getFullPath());
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void replacePipesWithSemicolons() throws Exception {



        String pipeContent = filesystemApi.readString(testFile.getAbsolutePath(), StandardCharsets.UTF_8);

        assertThat(pipeContent).contains("|");

        List<String> lines = filesystemApi.readAllLines(testFile.getAbsolutePath(), StandardCharsets.UTF_8);


        final List<String> editedString = lines.stream().map(l -> l.replace("|", ";")).collect(Collectors.toList());

        // final FileCommand cmd = new ByteArrayFileCommand(editedString, StandardCharsets.UTF_8);

        filesystemApi.saveFileContent(editedString, StandardCharsets.UTF_8, testFile.toPath());

        final String newContent = Files.readString(testFile.toPath(), StandardCharsets.UTF_8);

        assertThat(newContent).doesNotContain("|");
    }
}
