package de.xenadu.fsApi.api;

import de.xenadu.fsApi.beans.FilesystemApi;
import de.xenadu.fsApi.beans.PathWrapper;
import de.xenadu.fsApi.pojos.DownloadFileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DownloadApiTest {

    @Autowired
    PathWrapper pathWrapper;

    @Autowired
    FilesystemApi filesystemApi;

    @Test
    public void downloadTestFileTest() throws Exception {
        final URL resource = getClass().getResource("/testDir");
        final String path = resource.toURI().getPath();
        pathWrapper.addPath("test", path);

        DownloadFileResponse file = filesystemApi.getFileByPathNameAndFileName("test", "testfile.txt");

        final int sizeOfDownload = file.getByteArrayResource().getByteArray().length;


        assertThat(sizeOfDownload).isEqualTo(14); // foo says moo!\n
    }
}
