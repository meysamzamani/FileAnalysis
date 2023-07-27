package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import com.meysamzamani.file_analysis.models.FileTextLine;
import com.meysamzamani.file_analysis.models.Modes;
import com.meysamzamani.file_analysis.models.TextLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
public class LongestLineAnalysisService extends AnalysisService {

    @Autowired
    public LongestLineAnalysisService(IStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    public List<List<TextLine>> longestLines(Integer lineCount, Modes fileChoosingMode, MediaType responseMode) {
        List<Path> paths = fileSystemStorageService.loadAll();
        return findLongestLinesByPaths(paths, lineCount, fileChoosingMode, responseMode);
    }

    public List<List<TextLine>> findLongestLinesByPaths(List<Path> paths, int lineCount, Modes fileChoosingMode, MediaType responseMode) {
        try {
            List<Path> selectedPaths = findRelatedPathByModes(paths, fileChoosingMode);
            responseMode = checkAndValidateContentType(responseMode);
            return findLongestNLines(selectedPaths, lineCount, responseMode);
        }  catch (IOException exception) {
            throw new StorageFileNotFoundException("Could not read file", exception);
        }
    }

    public List<TextLine> longestLinesUnique(Integer lineCount, Modes fileChoosingMode, MediaType responseMode) {
        List<Path> paths = fileSystemStorageService.loadAll();
        return findLongestLinesUniqueByPaths(paths, lineCount, fileChoosingMode, responseMode);
    }

    public List<TextLine> findLongestLinesUniqueByPaths(List<Path> paths, int lineCount, Modes fileChoosingMode, MediaType responseMode) {
        try {
            List<Path> selectedPaths = findRelatedPathByModes(paths, fileChoosingMode);
            responseMode = checkAndValidateContentType(responseMode);
            Map<Integer, TextLine> longestLines = findLongestNLineUnique(selectedPaths, lineCount, responseMode);
            return longestLines.values().stream().toList();
        }  catch (IOException exception) {
            throw new StorageFileNotFoundException("Could not read file", exception);
        }
    }

    /**
     * This method is finding N the longest line inside one or more files
     *
     * @param paths location of files
     * @param limit maximum expected line
     * @param responseMode filter for show all information such as line number,file name and etc or just texts
     * @return a list collection contain text information inside another list that grouped by text length
     * @throws IOException
     */
    private List<List<TextLine>> findLongestNLines(List<Path> paths, int limit, MediaType responseMode) throws IOException {

        if (limit < ONE) {
            throw new IllegalArgumentException("Requested lines is ZERO");
        }

        Map<Integer, List<TextLine>> lengthAndTextLinesMapping = new TreeMap<>();
        AtomicInteger totalAddedItems = new AtomicInteger(ZERO);
        for (Path path : paths) {
            AtomicLong lineNumber = new AtomicLong(ZERO);
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    lineNumber.getAndIncrement();
                    int length = line.length();
                    int smallestLength = lengthAndTextLinesMapping.keySet().size() > ZERO ? lengthAndTextLinesMapping.keySet().iterator().next() : ZERO;
                    if (totalAddedItems.get() >= limit && length > smallestLength) {
                        List<TextLine> tempList = lengthAndTextLinesMapping.get(smallestLength);
                        if (tempList != null && tempList.size() > 0) {
                            tempList.remove(ZERO);
                        }
                        if (tempList == null || tempList.size() == ZERO) {
                            lengthAndTextLinesMapping.remove(smallestLength);
                        }
                        totalAddedItems.getAndDecrement();
                    }
                    if (totalAddedItems.get() < limit) {
                        List<TextLine> tempList = lengthAndTextLinesMapping.get(length) != null ? lengthAndTextLinesMapping.get(length) : new ArrayList<>();
                        if (responseMode.equals(MediaType.ALL)) {
                            FileTextLine fileTextLine = new FileTextLine(line, path.getFileName().toString(), line.length(), lineNumber.get(), findOftenOccurredLetter(line));
                            tempList.add(fileTextLine);
                            totalAddedItems.getAndIncrement();
                        } else {
                            TextLine textLine = new TextLine(line);
                            tempList.add(textLine);
                            totalAddedItems.getAndIncrement();
                        }
                        lengthAndTextLinesMapping.put(length, tempList);
                    }
                });
            }
        }
        return lengthAndTextLinesMapping.values().stream().toList();
    }

    /**
     * This method is finding unique N the longest line inside one or more files
     *
     * @param paths location of files
     * @param limit maximum expected line
     * @param responseMode filter for show all information such as line number,file name and etc or just texts
     * @return a map collection contain length of text as a key and text information as a value
     * @throws IOException
     */
    private Map<Integer, TextLine> findLongestNLineUnique(List<Path> paths, int limit, MediaType responseMode) throws IOException {

        if (limit < ONE) {
            throw new IllegalArgumentException("Requested lines is ZERO");
        }

        Map<Integer, TextLine> longestLines = new TreeMap<>();

        for (Path path : paths) {
            AtomicInteger smallLength = new AtomicInteger(ZERO);
            if (longestLines.size() > ZERO) {
                smallLength.set(longestLines.keySet().iterator().next());
            }

            AtomicLong lineNumber = new AtomicLong(ZERO);

            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    lineNumber.getAndIncrement();
                    int length = line.length();

                    if (longestLines.size() >= limit && length > smallLength.get() && !longestLines.containsKey(length)) {
                        longestLines.remove(smallLength.get());
                    }

                    if (!longestLines.containsKey(length) && longestLines.size() < limit) {
                        if (responseMode.equals(MediaType.ALL)) {
                            FileTextLine fileTextLine = new FileTextLine(line, path.getFileName().toString(), line.length(), lineNumber.get(), findOftenOccurredLetter(line));
                            longestLines.put(length, fileTextLine);
                        } else {
                            TextLine textLine = new TextLine(line);
                            longestLines.put(length, textLine);
                        }
                        smallLength.set(longestLines.keySet().iterator().next());
                    }

                });
            }
        }
        return longestLines;
    }
}
