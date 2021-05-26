package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.exception.CompositeException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextParserTest {

    private AbstractTextParser parser;

    @BeforeClass
    public void setUp() {
        LetterParser letterParser = new LetterParser();
        WordAndPunctuationParser wordAndPunctuationParser = new WordAndPunctuationParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();
        ParagraphParser paragraphParser = new ParagraphParser();

        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordAndPunctuationParser);
        wordAndPunctuationParser.setNextParser(letterParser);

        parser = paragraphParser;
    }

    @DataProvider(name = "text-provider")
    public Object[][] textProvider() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String[] filePaths = {
                "data/test_text.txt",
                "data/test_text_small.txt",
                "data/test_text_large.txt"
        };

        Object[][] data = new Object[filePaths.length][];

        for (int i = 0; i < filePaths.length; i++) {
            URL resource = classLoader.getResource(filePaths[i]);
            String fileAbsolutePath = new File(resource.getFile()).getAbsolutePath();
            Path path = Path.of(fileAbsolutePath);
            String text = Files.readString(path);
            data[i] = new Object[] { text };
        }

        return data;
    }

    @Test(dataProvider = "text-provider")
    public void testParse(String initialText) throws CompositeException {
        AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
        parser.parse(textComposite, initialText);
        String resultText = textComposite.toString();

        Assert.assertEquals(resultText, initialText);
    }
}
