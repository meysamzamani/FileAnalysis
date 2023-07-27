package com.meysamzamani.file_analysis.middlewares;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.List;

public class ResponseGeneratorFactory {

    private final List<MediaType> mediaTypes;

    public ResponseGeneratorFactory(List<MediaType> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public IResponseGenerator<Object> getResponseGenerator() throws HttpMediaTypeNotAcceptableException {
        if (mediaTypes.contains(MediaType.APPLICATION_JSON)) {
            return new JsonResponseGenerator<>();
        } else if (mediaTypes.contains(MediaType.APPLICATION_XML)) {
            return new XmlResponseGenerator<>();
        } else if (mediaTypes.contains(MediaType.TEXT_PLAIN)) {
            return new PlainTextResponseGenerator<>();
        } else {
            throw new HttpMediaTypeNotAcceptableException("Acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE + ", " +
                    MediaType.APPLICATION_XML + ", " + MediaType.TEXT_PLAIN_VALUE);
        }
    }

}
