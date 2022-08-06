package de.xenadu.fsApi.pojos;

import org.springframework.core.io.ByteArrayResource;

import javax.servlet.http.HttpServletResponse;

public interface DownloadFileResponse {

    /**
     *
     * @param description Description for the resource
     * @return File content as {@link ByteArrayResource}
     */
    ByteArrayResource getByteArrayResource(String description);

    /**
     *
     * @return File content as {@link ByteArrayResource}
     */
    ByteArrayResource getByteArrayResource();

    /**
     *
     * @param response The {@link HttpServletResponse} that will hold the file content
     * @param filename Sets the Content-Disposition Header to attachment with given filename
     */
    void setDownloadHeader(HttpServletResponse response, String filename);

    /**
     * Writes the file content directly to the {@link javax.servlet.ServletOutputStream}
     * @param response Response associated with the download
     * @param filename Filename in Content-Disposition Header
     */
    void setDownloadHeaderAndWriteToOutputStream(HttpServletResponse response, String filename);

    /**
     *
     * @return Filename of file that lives on the server
     */
    String getFilenameOnServer();
}
