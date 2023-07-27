package com.meysamzamani.file_analysis.controllers;

import com.meysamzamani.file_analysis.middlewares.ResponseGeneratorFactory;
import com.meysamzamani.file_analysis.models.Modes;
import com.meysamzamani.file_analysis.models.TextLine;
import com.meysamzamani.file_analysis.services.RandomLineAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path ="api/v1/random")
public class RandomLineAnalysisController {

    RandomLineAnalysisService randomLineService;

    @Autowired
    public RandomLineAnalysisController(RandomLineAnalysisService randomLineService) {
        this.randomLineService = randomLineService;
    }

    @GetMapping()
    public ResponseEntity<String> randomLine(@RequestParam(defaultValue = "LAST") Modes mode,
                                        @RequestHeader HttpHeaders headers) throws HttpMediaTypeNotAcceptableException {

        ResponseGeneratorFactory responseGeneratorFactory = new ResponseGeneratorFactory(headers.getAccept());
        TextLine singleRandomLine = randomLineService.randomLine(mode, headers.getContentType());
        return responseGeneratorFactory.getResponseGenerator().generateResponse(singleRandomLine);

    }

}
