package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import com.meysamzamani.file_analysis.models.FileTextLine;
import com.meysamzamani.file_analysis.models.Modes;
import com.meysamzamani.file_analysis.models.TextLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class RandomLineAnalysisServiceTest {

    @MockBean
    private IStorageService fileSystemStorageService;

    @TempDir Path testPath;

    @Test
    void shouldOneRandomLineEqualExpectedAndCorrectNotNullInstance() throws IOException {
        var randomLineAnalysisService = new RandomLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "AAAAAaaaakjhwjhjhw".getBytes());
        String expectedText = "AAAAAaaaakjhwjhjhw";

        TextLine textLine = randomLineAnalysisService.findRandomLineByRelatedPaths(List.of(givenFile), Modes.RANDOM, MediaType.APPLICATION_JSON);
        assertAll( () -> assertInstanceOf(TextLine.class, textLine),
                   () -> assertNotNull(textLine),
                   () -> assertEquals(expectedText, textLine.getText()));
    }

    @Test
    void shouldStorageExceptionWhenEmpty() {
        StorageFileNotFoundException thrown = assertThrows(StorageFileNotFoundException.class, () -> {
            var randomLineAnalysisService = new RandomLineAnalysisService(fileSystemStorageService);
            Path givenFile = testPath.resolve("test.txt");
            Files.write(givenFile, "".getBytes());
            randomLineAnalysisService.findRandomLineByRelatedPaths(List.of(givenFile), Modes.RANDOM, MediaType.ALL);
        }, "File is empty");
        assertEquals("File is empty", thrown.getMessage());
    }

    @Test
    void shouldOneRandomLineEqualFileTextLineObject() throws IOException {
        var randomLineAnalysisService = new RandomLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "AAAAAaaaakjhwjhjhw".getBytes());
        String expectedText = "AAAAAaaaakjhwjhjhw";

        TextLine textLine = randomLineAnalysisService.findRandomLineByRelatedPaths(List.of(givenFile), Modes.RANDOM, MediaType.ALL);
        assertInstanceOf(FileTextLine.class, textLine);
    }

    @Test
    void shouldOneRandomLineEqualLastFileInfo() throws IOException {
        var randomLineAnalysisService = new RandomLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "AAAAAaaaakjhwjhjhw \n test line 2 \n test line 3".getBytes());

        Path givenFile1 = testPath.resolve("test1.txt");
        Files.write(givenFile1, "TESTTESTTEST11111   TEST".getBytes());

        String expectedText = "TESTTESTTEST11111   TEST";

        TextLine textLine = randomLineAnalysisService.findRandomLineByRelatedPaths(List.of(givenFile, givenFile1), Modes.LAST, MediaType.ALL);
        assertAll( () -> assertInstanceOf(FileTextLine.class, textLine),
                () -> assertNotNull(textLine),
                () -> assertEquals(expectedText, textLine.getText()));
    }


}