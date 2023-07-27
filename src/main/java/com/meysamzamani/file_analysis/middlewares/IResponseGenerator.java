package com.meysamzamani.file_analysis.middlewares;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface IResponseGenerator<T extends Object> {
    HttpHeaders responseHeaders = new HttpHeaders();
    ResponseEntity<String> generateResponse(T entity);
}
