package de.xenadu.fsApi.configuration;

import de.xenadu.fsApi.beans.DefaultPathWrapper;
import de.xenadu.fsApi.beans.PathWrapper;
import de.xenadu.fsApi.beans.UniqueFilenameGenerator;
import de.xenadu.fsApi.beans.UniqueNameWithIndexGenerator;
import de.xenadu.fsApi.properties.FilesystemApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FsApiConfig {

    private final FilesystemApiProperties filesystemApiProperties;

    @Bean
    public UniqueFilenameGenerator uniqueFilenameGenerator() {
        return new UniqueNameWithIndexGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(PathWrapper.class)
    public PathWrapper pathWrapper() {
        final DefaultPathWrapper defaultPathWrapper = new DefaultPathWrapper(filesystemApiProperties.getPaths());
        
        if (filesystemApiProperties.isCheckPaths()) {
            defaultPathWrapper.checkPaths();
        }

        return defaultPathWrapper;
    }

}
