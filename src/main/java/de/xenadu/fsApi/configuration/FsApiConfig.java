package de.xenadu.fsApi.configuration;

import de.xenadu.fsApi.beans.DefaultPathWrapper;
import de.xenadu.fsApi.beans.PathWrapper;
import de.xenadu.fsApi.properties.FilesystemApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FsApiConfig {

    private final FilesystemApiProperties filesystemApiProperties;

    @Bean
    public PathWrapper pathWrapper() {
        return new DefaultPathWrapper(filesystemApiProperties.getPaths());
    }

}
