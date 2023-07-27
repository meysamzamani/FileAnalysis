package com.meysamzamani.file_analysis.models;

public class FileName {

    private final String name;

    public FileName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FileName valueOf (String name) {
        return new FileName(name);
    }
}
