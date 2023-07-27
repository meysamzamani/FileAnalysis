package com.meysamzamani.file_analysis.controllers;

import com.meysamzamani.file_analysis.middlewares.ResponseGeneratorFactory;
import com.meysamzamani.file_analysis.models.Modes;
import com.meysamzamani.file_analysis.models.TextLine;
import com.meysamzamani.file_analysis.services.LongestLineAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/v1/longest")
public class LongestLineAnalysisController {

    LongestLineAnalysisService longestLineService;

    @Autowired
    public LongestLineAnalysisController(LongestLineAnalysisService longestLineService) {
        this.longestLineService = longestLineService;
    }

    @GetMapping("/{lineCount}")
    public ResponseEntity<String> longestLines(@PathVariable("lineCount") Integer lineCount,
                                                       @RequestParam(defaultValue = "LAST") Modes mode,
                                                       @RequestHeader HttpHeaders headers) throws HttpMediaTypeNotAcceptableException {

        ResponseGeneratorFactory responseGeneratorFactory = new ResponseGeneratorFactory(headers.getAccept());
        List<List<TextLine>> longestLines = longestLineService.longestLines(lineCount, mode, headers.getContentType());
        return responseGeneratorFactory.getResponseGenerator().generateResponse(longestLines);
    }

    @GetMapping("/{lineCount}/unique")
    public ResponseEntity<String> longestLinesUnique(@PathVariable("lineCount") Integer lineCount,
                                               @RequestParam(defaultValue = "LAST") Modes mode,
                                               @RequestHeader HttpHeaders headers) throws HttpMediaTypeNotAcceptableException {

        ResponseGeneratorFactory responseGeneratorFactory = new ResponseGeneratorFactory(headers.getAccept());
        List<TextLine> longestLines = longestLineService.longestLinesUnique(lineCount, mode, headers.getContentType());
        return responseGeneratorFactory.getResponseGenerator().generateResponse(longestLines);
    }

}
