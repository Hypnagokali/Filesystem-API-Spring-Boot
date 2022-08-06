package de.xenadu.fsApi.beans;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

@SpringBootTest
class UniqueNameWithIndexGeneratorTest {

    @Autowired
    PathWrapper pathWrapper;

    @Autowired
    FilesystemApi filesystemApi;

    @BeforeEach
    public void setUp() throws Exception {
        final URL resource = getClass().getResource("/testDir");
        final String path = resource.toURI().getPath();
        pathWrapper.addPath("testDir", path);
    }

    @AfterEach
    public void tearDown() throws Exception {

    }

    @Test
    public void generateANewTestFile() throws Exception {

    }
}