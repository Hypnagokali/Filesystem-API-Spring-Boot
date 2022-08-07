package de.xenadu.fsApi.api;

import de.xenadu.fsApi.beans.FilesystemApi;
import de.xenadu.fsApi.beans.PathWrapper;
import de.xenadu.fsApi.types.FsFile;
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
public class TypedUploadTest {

    @Autowired
    FilesystemApi filesystemApi;

    @Autowired
    PathWrapper pathWrapper;

    @Test
    public void typedUploadTest() throws Exception {
        final URI uri = Objects.requireNonNull(getClass().getResource("/testDir")).toURI();
        MultipartFile file = new MockMultipartFile("file", "orig.txt", "text/plain", "foo says moo!".getBytes(StandardCharsets.UTF_8));

        pathWrapper.addPath("test", new File(uri).getAbsolutePath());
        final Path path = pathWrapper.getPathByName("test").orElseThrow();

        final MyUpload upload = filesystemApi.uploadFileTyped(file, path.toString(), "moo.txt", new MyUpload());


        assertThat(upload.getOriginalName()).isEqualTo("orig.txt");
        assertThat(upload.getFullPath()).isEqualTo(Path.of(path + "/moo.txt").toString());
    }

    public static class MyUpload implements FsFile {

        private String fullPath;
        private String origName;
        private long size;

        @Override
        public void populate(String fullPath, String originalName, long size) {
            this.fullPath = fullPath;
            this.origName = originalName;
            this.size = size;
        }

        @Override
        public String getFullPath() {
            return fullPath;
        }

        @Override
        public String getFilename() {
            return "not implemented";
        }

        @Override
        public String getOriginalName() {
            return origName;
        }

        @Override
        public long getSize() {
            return size;
        }
    }
}
