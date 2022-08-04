package de.xenadu.fsApi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource(value = "classpath:filesystem.properties", ignoreResourceNotFound = true)
@ConfigurationProperties("filesystem-api")
@Data
public class FilesystemApiProperties {

    private Map<String, String> paths;

}
