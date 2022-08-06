package de.xenadu.fsApi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySources(
        {
                @PropertySource(value = "classpath:filesystem.properties", ignoreResourceNotFound = true),
                @PropertySource(value = "file:./filesystem.properties", ignoreResourceNotFound = true)
        })
@ConfigurationProperties("filesystem-api")
@Data
public class FilesystemApiProperties {

    private boolean checkPaths = false;
    private Map<String, String> paths = new HashMap<>();

}
