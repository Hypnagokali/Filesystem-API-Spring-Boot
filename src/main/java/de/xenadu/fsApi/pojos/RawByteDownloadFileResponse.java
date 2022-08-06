package de.xenadu.fsApi.pojos;

import org.springframework.core.io.ByteArrayResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RawByteDownloadFileResponse implements DownloadFileResponse {

    private final String filename;
    private final byte[] content;

    public RawByteDownloadFileResponse(File file) {
        filename = file.getName();

        try {
            content = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Could not read bytes of file: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public ByteArrayResource getByteArrayResource(String description) {
        return new ByteArrayResource(content, description);
    }

    @Override
    public ByteArrayResource getByteArrayResource() {
        return new ByteArrayResource(content);
    }

    @Override
    public void setDownloadHeader(HttpServletResponse response, String filename) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength(content.length);
    }

    @Override
    public void setDownloadHeaderAndWriteToOutputStream(HttpServletResponse response, String filename) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength(content.length);
        response.setCharacterEncoding("UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(content);
            outputStream.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Could not write to output stream of response", e);
        }
    }

    @Override
    public String getFilenameOnServer() {
        return filename;
    }
}
