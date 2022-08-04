package de.xenadu.fsApi.api;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class DownloadApiTest {


    @Test
    public void downloadTestFileTest() throws Exception {
        final URL resource = getClass().getResource("/uploaded/some/testfile.txt");
        final String path = resource.getPath();

        System.out.println();
    }
}
