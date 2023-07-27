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

class LongestLineAnalysisServiceTest {

    @MockBean
    private IStorageService fileSystemStorageService;

    @TempDir
    Path testPath;

    @Test
    void shouldTwoLongestLineUniqueEqualExpectedAndCorrectNotNullInstance() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, ("AAAAAaaaakjhwjhjhwasasaasewrwr2324242424\n" +
                                "892874687648376483648638763786487364873687886253765276\n" +
                                "892874687648376483648638763786487364873687886253765276").getBytes());
        String expectedText = "892874687648376483648638763786487364873687886253765276";
        int expectedLines = 2;

        List<TextLine> textLines = longestLineAnalysisService.findLongestLinesUniqueByPaths(List.of(givenFile), 2, Modes.LAST, MediaType.APPLICATION_JSON);

        assertAll( () -> assertInstanceOf(TextLine.class, textLines.get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedLines, textLines.size()),
                () -> assertEquals(expectedText, textLines.get(1).getText()));
    }

    @Test
    void shouldIllegalArgumentExceptionWhenLineCountIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);
            Path givenFile = testPath.resolve("test.txt");
            Files.write(givenFile, " ".getBytes());
            longestLineAnalysisService.findLongestLinesUniqueByPaths(List.of(givenFile),0, Modes.RANDOM, MediaType.ALL);
        }, "Requested lines is ZERO");
        assertEquals("Requested lines is ZERO", thrown.getMessage());
    }

    @Test
    void shouldStorageExceptionWhenFileNotReadable() {
        StorageFileNotFoundException thrown = assertThrows(StorageFileNotFoundException.class, () -> {
            var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);
            Path givenFile = testPath.resolve("test.txt");
            longestLineAnalysisService.findLongestLinesUniqueByPaths(List.of(givenFile),2, Modes.RANDOM, MediaType.ALL);
        }, "Could not read file");
        assertEquals("Could not read file", thrown.getMessage());
    }

    @Test
    void shouldTenLongestUniqueLinesEqualFileTextLineArrayWithOneSize() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "MEYSAMMTESTMEYSAMCOCUSTESTtest".getBytes());
        int expectedLines = 1;

        List<TextLine> textLines = longestLineAnalysisService.findLongestLinesUniqueByPaths(List.of(givenFile), 10, Modes.LAST, MediaType.ALL);
        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0)),
                   () -> assertEquals(expectedLines, textLines.size()));
    }

    @Test
    void shouldTwoLongestUniqueLinesEqualOneLineWithLastFileInfo() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "AAAAAaaaakjhwjhjhw \n test line 2 \n test line 3".getBytes());

        Path givenFile1 = testPath.resolve("test1.txt");
        Files.write(givenFile1, "TESTTESTTEST11111   TEST".getBytes());

        String expectedText = "TESTTESTTEST11111   TEST";
        int expectedLines = 1;

        List<TextLine> textLines = longestLineAnalysisService.findLongestLinesUniqueByPaths(List.of(givenFile, givenFile1), 2, Modes.LAST, MediaType.ALL);

        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedLines, textLines.size()),
                () -> assertEquals(expectedText, textLines.get(0).getText()));
    }

    @Test
    void shouldTwoLongestLineEqualExpectedAndCorrectNotNullInstance() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, ("AAAAAaaaakjhwjhjhwasasaasewrwr2324242424\n" +
                "892874687648376483648638763786487364873687886253765276\n" +
                "892874687648376483648638763786487364873687886253765276").getBytes());
        String expectedText = "892874687648376483648638763786487364873687886253765276";
        int expectedLines = 2;

        List<List<TextLine>> textLines = longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile), 2, Modes.LAST, MediaType.APPLICATION_JSON);

        assertAll( () -> assertInstanceOf(TextLine.class, textLines.get(0).get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedLines, textLines.get(0).size()),
                () -> assertEquals(expectedText, textLines.get(0).get(1).getText()));
    }

    @Test
    void shouldIllegalArgumentExceptionWhenLineCountWasZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);
            Path givenFile = testPath.resolve("test.txt");
            Files.write(givenFile, " ".getBytes());
            longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile),0, Modes.RANDOM, MediaType.ALL);
        }, "Requested lines is ZERO");
        assertEquals("Requested lines is ZERO", thrown.getMessage());
    }

    @Test
    void shouldStorageExceptionThrownWhenFileNotReadable() {
        StorageFileNotFoundException thrown = assertThrows(StorageFileNotFoundException.class, () -> {
            var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);
            Path givenFile = testPath.resolve("test.txt");
            longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile),2, Modes.RANDOM, MediaType.ALL);
        }, "Could not read file");
        assertEquals("Could not read file", thrown.getMessage());
    }

    @Test
    void shouldTenLongestLinesEqualFileTextLineArrayWithOneSize() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "MEYSAMMTESTMEYSAMCOCUSTESTtest".getBytes());
        int expectedLines = 1;

        List<List<TextLine>> textLines = longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile), 10, Modes.LAST, MediaType.ALL);
        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0).get(0)),
                () -> assertEquals(expectedLines, textLines.size()));
    }

    @Test
    void shouldTwoLongestLinesEqualOneLineWithLastFileInfo() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "AAAAAaaaakjhwjhjhw \n test line 2 \n test line 3".getBytes());

        Path givenFile1 = testPath.resolve("test1.txt");
        Files.write(givenFile1, "TESTTESTTEST11111   TEST".getBytes());

        String expectedText = "TESTTESTTEST11111   TEST";
        int expectedLines = 1;

        List<List<TextLine>> textLines = longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile, givenFile1), 2, Modes.LAST, MediaType.ALL);

        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0).get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedLines, textLines.size()),
                () -> assertEquals(expectedText, textLines.get(0).get(0).getText()));
    }

    @Test
    void shouldTenLongestLinesEqualTwoArrayWithFiveElement() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "Test1\nTest2\nTest3\nTest4\nTest5".getBytes());

        Path givenFile1 = testPath.resolve("test1.txt");
        Files.write(givenFile1, "Test21\nTest22\nTest23\nTest24\nTest25".getBytes());

        int expectedArray = 2;
        int expectedElementSize = 5;

        List<List<TextLine>> textLines = longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile, givenFile1), 10, Modes.ALL, MediaType.ALL);

        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0).get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedArray, textLines.size()),
                () -> assertEquals(expectedElementSize, textLines.get(0).size()),
                () -> assertEquals(expectedElementSize, textLines.get(1).size()));
    }

    @Test
    void shouldTenLongestLinesEqualTenArrayWithOneElementAndCorrectLongestText() throws IOException {
        var longestLineAnalysisService = new LongestLineAnalysisService(fileSystemStorageService);

        Path givenFile = testPath.resolve("test.txt");
        Files.write(givenFile, "Test1\nTest22\nTest333\nTest4444\nTest55555".getBytes());

        Path givenFile1 = testPath.resolve("test1.txt");
        Files.write(givenFile1, "Test666666\nTest7777777\nTest88888888\nTest999999999\nTest0000000000".getBytes());

        int expectedArray = 10;
        int expectedElementSize = 1;
        int expectedLongestLength = 14;
        String expectedLongestText = "Test0000000000";

        List<List<TextLine>> textLines = longestLineAnalysisService.findLongestLinesByPaths(List.of(givenFile, givenFile1), 10, Modes.ALL, MediaType.ALL);

        assertAll( () -> assertInstanceOf(FileTextLine.class, textLines.get(0).get(0)),
                () -> assertNotNull(textLines),
                () -> assertEquals(expectedArray, textLines.size()),
                () -> assertEquals(expectedElementSize, textLines.get(0).size()),
                () -> assertEquals(expectedElementSize, textLines.get(1).size()),
                () -> assertEquals(expectedLongestLength, textLines.get(9).get(0).getText().length()),
                () -> assertEquals(expectedLongestText, textLines.get(9).get(0).getText()));
    }
}