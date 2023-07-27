package com.meysamzamani.file_analysis.middlewares;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class PlainTextResponseGenerator<T> implements IResponseGenerator<T> {

    public PlainTextResponseGenerator() {
        responseHeaders.set("Content-Type", MediaType.TEXT_PLAIN_VALUE);
    }

    @Override
    public ResponseEntity<String> generateResponse(T entity) {
        String response = entity.toString();
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
}
