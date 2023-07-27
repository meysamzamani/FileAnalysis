package com.meysamzamani.file_analysis.middlewares;

import com.meysamzamani.file_analysis.models.Modes;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Modes> {
    @Override
    public Modes convert(String source) {
        try {
            return Modes.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
