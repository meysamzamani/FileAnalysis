package com.meysamzamani.file_analysis.models;

/**
 * Child Class of TextLine for return full information of a line
 */
public class FileTextLine extends TextLine {

    public FileTextLine(String text, String fileName, Integer textLength, Long lineNumber, String oftenOccurredLetter) {
        super(text);
        this.fileName = fileName;
        this.textLength = textLength;
        this.lineNumber = lineNumber;
        this.oftenOccurredLetter = oftenOccurredLetter;
    }

    private final String fileName;
    private final Integer textLength;
    private final Long lineNumber;
    private String oftenOccurredLetter;

    public String getFileName() {
        return fileName;
    }

    public Integer getTextLength() {
        return textLength;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public String getOftenOccurredLetter() {
        return oftenOccurredLetter;
    }

    public void setOftenOccurredLetter(String oftenOccurredLetter) {
        this.oftenOccurredLetter = oftenOccurredLetter;
    }

    @Override
    public String toString() {
        return "FileTextLine{" +
                "text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", textLength=" + textLength +
                ", lineNumber=" + lineNumber +
                ", oftenOccurredLetter='" + oftenOccurredLetter + '\'' +
                '}' + "\n";
    }
}
