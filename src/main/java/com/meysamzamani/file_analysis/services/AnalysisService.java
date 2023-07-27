package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import com.meysamzamani.file_analysis.models.Modes;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.util.*;

public class AnalysisService {

    IStorageService fileSystemStorageService;
    public final static int ONE = 1;
    public final static int ZERO = 0;

    public long calculateRandomNumber(long maxNumber) {
        maxNumber = Math.max(ONE, maxNumber);
        Random random = new Random();
        return random.nextLong(ZERO, maxNumber);
    }

    public int calculateRandomNumber(int maxNumber) {
        return (int) calculateRandomNumber((long) maxNumber);
    }

    public List<Path> findRelatedPathByModes(List<Path> paths, Modes mode) {
        if (paths == null || paths.size() < ONE) {
            throw new StorageFileNotFoundException("Directory is empty");
        }

        if (mode.equals(Modes.RANDOM)) {
            return List.of(paths.get(calculateRandomNumber(paths.size()-ONE)));
        }  else if (mode.equals(Modes.ALL)) {
            return paths;
        } else {
            return List.of(paths.get(paths.size()-ONE));
        }
    }

    public String findOftenOccurredLetter(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        Map<Character, Integer> visitedLetter = new HashMap<>();
        char oftenLetter = text.charAt(0);
        int count = 1;
        for (int i = 0; i < text.length() ; i++) {
            char letter = text.charAt(i);
            visitedLetter.put(letter, visitedLetter.getOrDefault(letter, ZERO) + ONE);
            int visitedLetterValue =  visitedLetter.get(letter);
            if (count < visitedLetterValue) {
                oftenLetter = letter;
                count = visitedLetterValue;
            }
        }
        return Character.toString(oftenLetter);
    }

    public MediaType checkAndValidateContentType(MediaType contentType) {
        return Objects.requireNonNullElse(contentType, MediaType.APPLICATION_JSON);
    }

}
