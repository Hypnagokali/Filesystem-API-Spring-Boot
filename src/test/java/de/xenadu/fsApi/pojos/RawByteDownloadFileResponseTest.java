package de.xenadu.fsApi.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class RawByteDownloadFileResponseTest {


    File file;

    @BeforeEach
    public void setUp() throws Exception {
        final URI uri = Objects.requireNonNull(getClass().getResource("/testDir/testfile.txt")).toURI();
        file = new File(uri);
    }

    @Test
    public void filenameTest() throws Exception {
        DownloadFileResponse fileResponse = new RawByteDownloadFileResponse(file);

        assertThat(fileResponse.getFilenameOnServer()).isEqualTo("testfile.txt");
    }

    @Test
    public void testFileContent() throws Exception {
        DownloadFileResponse fileResponse = new RawByteDownloadFileResponse(file);
        final byte[] byteArray = fileResponse.getByteArrayResource().getByteArray();

        final String content = new String(byteArray, StandardCharsets.UTF_8);

        assertThat(content).isEqualTo("foo says moo!\n");
    }

    @Test
    public void writeToOutputStreamTest() throws Exception {
        DownloadFileResponse fileResponse = new RawByteDownloadFileResponse(file);
        HttpServletResponse response = new MockHttpServletResponse();
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        fileResponse.setDownloadHeaderAndWriteToOutputStream(responseWrapper, "download.pdf");

        final String res = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

        assertThat(res).isEqualTo("foo says moo!\n");
        assertThat(responseWrapper.getHeader("Content-Disposition")).isEqualTo("attachment; filename=\"download.pdf\"");
    }
}