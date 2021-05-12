package com.chocobo.composite.reader;

import com.chocobo.composite.exception.CompositeException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextComponentReader {

    public String readTextFromFile(String filePath) throws CompositeException {
        try {
            return Files.readString(Path.of(ClassLoader.getSystemResource(filePath).toURI()));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new CompositeException(e);
        }
    }
}
