package de.xenadu.fsApi.api;

import de.xenadu.fsApi.beans.FilesystemApi;
import de.xenadu.fsApi.types.FsFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UploadApiTest {

    @Autowired
    FilesystemApi filesystemApi;

    File uploadDirectory;

    @BeforeEach
    public void setUp() throws Exception {
        final URI uri = Objects.requireNonNull(getClass().getResource("/testDir")).toURI();
        uploadDirectory = new File(uri);
    }

    @AfterEach
    public void tearDown() throws Exception {
        final File uploadedFile = new File(this.uploadDirectory.getPath() + "/" + "a_new_upload.txt");
        if (uploadedFile.exists()) {
            uploadedFile.delete();
        }
    }

    @Test
    public void uploadAFileTest() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file",
                "orig.txt",
                "text/plain",
                "a new file\n".getBytes(StandardCharsets.UTF_8));

        final FsFile fsFile = filesystemApi.uploadFile(file, this.uploadDirectory.getAbsolutePath(), "a_new_upload.txt");

        String fullPath = this.uploadDirectory.getPath() + "/" + "a_new_upload.txt";
        assertThat(fsFile.getFullPath()).isEqualTo(Path.of(fullPath).toString());
    }
}
