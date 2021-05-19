package com.chocobo.composite._main;

import com.chocobo.composite.comparator.ParagraphSentenceAmountComparator;
import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;
import com.chocobo.composite.parser.AbstractTextParser;
import com.chocobo.composite.parser.TextParserFactory;
import com.chocobo.composite.reader.TextComponentReader;
import com.chocobo.composite.service.TextService;
import com.chocobo.composite.service.impl.TextServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final String FILE_PATH = "data/text.txt";
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            TextComponentReader reader = new TextComponentReader();
            String text = reader.readTextFromFile(FILE_PATH);

            AbstractTextParser parser = TextParserFactory.createParser();
            TextComposite textComposite = new TextComposite(TextComponentType.TEXT);
            parser.parse(textComposite, text);
            System.out.println(textComposite);

            String textCompositeString = textComposite.toString();
            System.out.println("Restored text equals initial text: " + text.equals(textCompositeString));

            TextService service = new TextServiceImpl();
            ParagraphSentenceAmountComparator paragraphComparator = new ParagraphSentenceAmountComparator();
            List<AbstractTextComponent> sortedParagraphs = service.sortParagraphs(textComposite, paragraphComparator);
            System.out.println("List of paragraphs sorted by sentence amount: " + sortedParagraphs);
        } catch (CompositeException e) {
            logger.error("Unexpected exception: ", e);
        }
    }
}
