package com.chocobo.composite._main;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.exception.CompositeException;
import com.chocobo.composite.parser.AbstractTextParser;
import com.chocobo.composite.parser.TextParserFactory;
import com.chocobo.composite.reader.TextComponentReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final String FILE_PATH = "data/text.txt";
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            TextComponentReader reader = new TextComponentReader();
            String text = reader.readTextFromFile(FILE_PATH);

            AbstractTextParser parser = TextParserFactory.createParser();
            AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
            parser.parse(textComposite, text);
            System.out.println(textComposite);

            String textCompositeString = textComposite.toString();
            logger.info("Restored text equals initial text: " + text.equals(textCompositeString));
        } catch (CompositeException e) {
            logger.error("Unexpected exception: ", e);
        }
    }
}
