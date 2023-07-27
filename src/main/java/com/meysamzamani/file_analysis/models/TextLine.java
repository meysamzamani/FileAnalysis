package com.meysamzamani.file_analysis.models;

/**
 * Parent Class for return simple information of a line
 */
public class TextLine {
    String text;

    public TextLine(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return "TextLine{" +
                "text='" + text + '\'' +
                '}' + "\n";
    }
}
