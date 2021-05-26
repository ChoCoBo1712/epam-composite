package com.chocobo.composite.service.impl;

import com.chocobo.composite.comparator.ParagraphSentenceAmountComparator;
import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.exception.CompositeException;
import com.chocobo.composite.parser.*;
import com.chocobo.composite.service.TextService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextServiceImplTest {

    private TextService service;
    ParagraphSentenceAmountComparator paragraphComparator;
    private AbstractTextParser textParser;
    private AbstractTextParser sentenceParser;
    private List<String> initialTexts;

    @BeforeClass
    public void setUp() throws IOException {
        service = new TextServiceImpl();
        ClassLoader classLoader = getClass().getClassLoader();
        String[] filePaths = {
                "data/test_text.txt",
        };

        initialTexts = new ArrayList<>();
        for (String filePath : filePaths) {
            URL resource = classLoader.getResource(filePath);
            String fileAbsolutePath = new File(resource.getFile()).getAbsolutePath();
            Path path = Path.of(fileAbsolutePath);
            String text = Files.readString(path);
            initialTexts.add(text);
        }

        LetterParser letterParser = new LetterParser();
        WordAndPunctuationParser wordAndPunctuationParser = new WordAndPunctuationParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();
        ParagraphParser paragraphParser = new ParagraphParser();

        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordAndPunctuationParser);
        wordAndPunctuationParser.setNextParser(letterParser);

        textParser = paragraphParser;
        this.sentenceParser = sentenceParser;
        paragraphComparator = new ParagraphSentenceAmountComparator();
    }

    @DataProvider(name = "sentences-amount-provider")
    public Object[][] sentencesAmountProvider() {
        return new Object[][] {
                { initialTexts.get(0), new ArrayList<>() {{ add(1); add(2); }} }
        };
    }

    @DataProvider(name = "longest-word-provider")
    public Object[][] longestWordProvider() {
        return new Object[][] {
                { initialTexts.get(0), 2 }
        };
    }

    @DataProvider(name = "sentences-left-provider")
    public Object[][] sentencesLeftProvider() {
        return new Object[][] {
                { initialTexts.get(0), 2, 5 }
        };
    }

    @DataProvider(name = "words-count-provider")
    public Object[][] wordsCountProvider() {
        return new Object[][] {
                { initialTexts.get(0), new HashMap<String, Integer>() {{ put("Lorem", 1); put("ipsum", 1);
                    put("dolor", 2); put("sit", 1); put("enim", 2); put("consectetur", 2);
                    put("malesuada", 2); put("elit", 1); put("Mauris", 1); put("et", 2);
                    put("molestie", 2); put("Integer", 1); put("felis", 1); }} }
        };
    }

    @DataProvider(name = "sentence-for-vowels-test-provider")
    public Object[][] sentenceForVowelsTestProvider() {
        return new Object[][] {
                { "Sentence for vowels.", 6 },
                { "Hello world!", 3 }
        };
    }

    @DataProvider(name = "sentence-for-consonants-test-provider")
    public Object[][] sentenceForConsonantsTestProvider() {
        return new Object[][] {
                { "Sentence for consonants.", 14 },
                { "Hi there!", 4 }
        };
    }

    @Test(dataProvider = "sentences-amount-provider")
    public void sortParagraphsTest(String initialText, List<Integer> expectedWordsCountList)
            throws CompositeException {
        AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
        textParser.parse(textComposite, initialText);
        List<Integer> actualWordsCountList = service.sortParagraphs(textComposite, paragraphComparator)
                .stream()
                .map(component -> component.getChildren().size())
                .collect(Collectors.toList());

        Assert.assertEquals(actualWordsCountList, expectedWordsCountList);
    }

    @Test(dataProvider = "longest-word-provider")
    public void findSentencesWithLongestWordTest(String initialText, int expectedCount)
            throws CompositeException {
        AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
        textParser.parse(textComposite, initialText);
        List<AbstractTextComponent> sentences = service.findSentencesWithLongestWord(textComposite);
        int actualCount = sentences.size();

        Assert.assertEquals(actualCount, expectedCount);
    }

    @Test(dataProvider = "sentences-left-provider")
    public void removeSentencesWithWordsAmountLessThanTest(
            String initialText, int expectedSentenceCount, int lessThan
    ) throws CompositeException {
        AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
        textParser.parse(textComposite, initialText);
        AbstractTextComponent modifiedComposite = service.removeSentencesWithWordsAmountLessThan(textComposite, lessThan);
        long actualSentenceCount = modifiedComposite.getChildren()
                .stream()
                .mapToLong(component -> component.getChildren().size())
                .sum();

        Assert.assertEquals(actualSentenceCount, expectedSentenceCount);
    }

    @Test(dataProvider = "words-count-provider")
    public void countIdenticalWordsTest(String initialText, Map<String, Integer> expectedOccurrences)
            throws CompositeException {
        AbstractTextComponent textComposite = new TextComposite(TextComponentType.TEXT);
        textParser.parse(textComposite, initialText);
        Map<String, Integer> actualOccurrences = service.countIdenticalWords(textComposite);

        Assert.assertEquals(actualOccurrences, expectedOccurrences);
    }

    @Test(dataProvider = "sentence-for-vowels-test-provider")
    public void countVowelsInSentenceTest(String initialSentence, long expected) throws CompositeException {
        AbstractTextComponent paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        sentenceParser.parse(paragraph, initialSentence);
        AbstractTextComponent sentenceComposite = paragraph.getChildren()
                .stream()
                .findFirst()
                .get();

        long actual = service.countVowelsInSentence(sentenceComposite);

        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "sentence-for-consonants-test-provider")
    public void countConsonantsInSentenceTest(String initialSentence, long expected) throws CompositeException {
        AbstractTextComponent paragraph = new TextComposite(TextComponentType.PARAGRAPH);
        sentenceParser.parse(paragraph, initialSentence);
        AbstractTextComponent sentenceComposite = paragraph.getChildren()
                .stream()
                .findFirst()
                .get();

        long actual = service.countConsonantsInSentence(sentenceComposite);

        Assert.assertEquals(actual, expected);
    }
}
