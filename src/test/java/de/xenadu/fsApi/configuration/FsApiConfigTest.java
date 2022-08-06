package de.xenadu.fsApi.configuration;

import de.xenadu.fsApi.beans.PathWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FsApiConfigTest {

    @Autowired
    PathWrapper pathWrapper;

    @Test
    public void applicationProperties_getTempPathTest() throws Exception {
        final Optional<Path> temp = pathWrapper.getPathByName("temp");

        assertThat(temp).isNotEmpty();
        assertThat(temp.get()).isEqualTo(Path.of("/data/temp"));
    }

    @Test
    public void applicationProperties_getTempUploadTest() throws Exception {
        final Optional<Path> temp = pathWrapper.getPathByName("upload");

        assertThat(temp).isNotEmpty();
        assertThat(temp.get()).isEqualTo(Path.of("/data/upload"));
    }

    @Test
    public void filesystemProperties_getAnotherTest() throws Exception {
        final Optional<Path> temp = pathWrapper.getPathByName("another");

        assertThat(temp).isNotEmpty();
        assertThat(temp.get()).isEqualTo(Path.of("/and/another/path"));
    }
}