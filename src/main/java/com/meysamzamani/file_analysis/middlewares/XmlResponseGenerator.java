package com.meysamzamani.file_analysis.middlewares;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class XmlResponseGenerator<T> implements IResponseGenerator<T> {

    public XmlResponseGenerator() {
        responseHeaders.set("Content-Type", MediaType.APPLICATION_XML_VALUE);
    }

    @Override
    public ResponseEntity<String> generateResponse(T entity) {
        try {
            ObjectMapper mapper = new XmlMapper();
            String response = mapper.writeValueAsString(entity);
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
        } catch (JsonProcessingException exception) {
            return new ResponseEntity<>("Failed to parse object",responseHeaders ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
