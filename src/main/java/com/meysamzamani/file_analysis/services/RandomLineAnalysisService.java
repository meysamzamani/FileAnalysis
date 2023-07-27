package com.meysamzamani.file_analysis.services;

import com.meysamzamani.file_analysis.exceptions.StorageFileNotFoundException;
import com.meysamzamani.file_analysis.models.FileTextLine;
import com.meysamzamani.file_analysis.models.Modes;
import com.meysamzamani.file_analysis.models.TextLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Service
public class RandomLineAnalysisService extends AnalysisService {

    @Autowired
    public RandomLineAnalysisService(IStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    public TextLine randomLine(Modes fileChoosingMode, MediaType responseMode) {
        try {
            List<Path> paths = fileSystemStorageService.loadAll();
            return findRandomLineByRelatedPaths(paths, fileChoosingMode, responseMode);
        } catch (IOException exception) {
            throw new StorageFileNotFoundException("Could not read file: ", exception);
        }
    }

    public TextLine findRandomLineByRelatedPaths(List<Path> paths, Modes fileChoosingMode, MediaType responseMode) throws IOException {
        fileChoosingMode = fileChoosingMode.equals(Modes.ALL) ? Modes.RANDOM : Modes.LAST;
        List<Path> selectedPaths = findRelatedPathByModes(paths, fileChoosingMode);
        return readOneRandomLineFromPath(selectedPaths.iterator().next(), responseMode);
    }

    /**
     * This method is locating for the one random line inside one file
     *
     * @param path location of files
     * @param responseMode filter for show all information such as line number,file name and etc or just texts
     * @return one random text line with another information
     * @throws IOException
     */
    private TextLine readOneRandomLineFromPath(Path path, MediaType responseMode) throws IOException {
        long maxLineNumber;

        responseMode = checkAndValidateContentType(responseMode);

        try (Stream<String> fileStream = Files.lines(path)) {
            maxLineNumber = fileStream.count();
        }

        try (Stream<String> fileStream = Files.lines(path)) {
            long randomLine = Math.max(calculateRandomNumber(maxLineNumber)-ONE, ZERO);
            Optional<String> line = fileStream.skip(randomLine).findFirst();
            if (line.isPresent()) {
                String randomText = line.get();
                if (responseMode.equals(MediaType.ALL)) {
                    return new FileTextLine(randomText, path.getFileName().toString(), randomText.length(), randomLine+ONE, findOftenOccurredLetter(randomText));
                } else {
                    return new TextLine(randomText);
                }
            } else {
                throw new StorageFileNotFoundException("File is empty");
            }
        }
    }

}
