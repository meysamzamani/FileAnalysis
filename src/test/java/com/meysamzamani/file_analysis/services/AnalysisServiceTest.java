package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import com.meysamzamani.file_analysis.models.Modes;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisServiceTest {

    @Test
    void shouldReturnIntegerNumberBetweenZeroAndTen() {
        var analysisService = new AnalysisService();
        int max = 10;
        int min = 0;
        int actual = analysisService.calculateRandomNumber(max);
        assertTrue(actual >= min && actual <= max);
    }

    @Test
    void shouldReturnIntegerZero() {
        var analysisService = new AnalysisService();
        int max = 0;
        int expected = 0;
        int actual = analysisService.calculateRandomNumber(max);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLongNumberBetweenZeroAndTen() {
        var analysisService = new AnalysisService();
        long max = 10L;
        long min = 0L;
        long actual = analysisService.calculateRandomNumber(max);
        assertTrue(actual >= min && actual <= max);
    }

    @Test
    void shouldReturnLongZero() {
        var analysisService = new AnalysisService();
        long max = 0L;
        long expected = 0L;
        long actual = analysisService.calculateRandomNumber(max);
        assertEquals(expected, actual);
    }

    @Test
    void testFindOftenOccurredLetter() {
        var analysisService = new AnalysisService();
        String given = "AAAasasasasa aad sf jhiueriueh ehnmsnd,mn";
        String expected = "a";
        assertEquals(expected, analysisService.findOftenOccurredLetter(given));
    }

    @Test
    void shouldReturnEmptyWhenGivenNull() {
        var analysisService = new AnalysisService();
        String expected = "";
        assertEquals(expected, analysisService.findOftenOccurredLetter(null));
    }

    @Test
    void shouldReturnJSONWhenNull() {
        var analysisService = new AnalysisService();
        MediaType expected = MediaType.APPLICATION_JSON;
        assertEquals(expected, analysisService.checkAndValidateContentType(null));
    }

    @Test
    void shouldReturnXmlWhenXml() {
        var analysisService = new AnalysisService();
        MediaType given = MediaType.APPLICATION_XML;
        MediaType expected = MediaType.APPLICATION_XML;
        assertEquals(expected, analysisService.checkAndValidateContentType(given));
    }

    @Test
    void shouldStorageExceptionWhenEmpty() {
        var analysisService = new AnalysisService();
        StorageFileNotFoundException thrown = assertThrows(StorageFileNotFoundException.class, () -> {
            analysisService.findRelatedPathByModes(null, Modes.ALL);
        }, "Directory is empty");
        assertEquals("Directory is empty", thrown.getMessage());
    }
}