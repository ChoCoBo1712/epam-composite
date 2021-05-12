package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.Symbol;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAndPunctuationParser extends AbstractTextParser {

    private static final String WORD_REGEX = "[^.!?,]+";
    private static final String PUNCTUATION_REGEX = "\\.|!|\\?|\\.{3}|,";

    @Override
    public void parse(AbstractTextComponent component, String data) throws CompositeException {
        Matcher wordMatcher = Pattern.compile(WORD_REGEX).matcher(data);
        Matcher punctuationMatcher = Pattern.compile(PUNCTUATION_REGEX).matcher(data);

        while (wordMatcher.find()) {
            String word = wordMatcher.group();
            TextComposite wordComponent = new TextComposite(TextComponentType.WORD);
            component.add(wordComponent);
            nextParser.parse(wordComponent, word);
        }

        while (punctuationMatcher.find()) {
            String punctuation = punctuationMatcher.group();
            AbstractTextComponent punctuationComponent = new Symbol(punctuation, TextComponentType.PUNCTUATION);
            component.add(punctuationComponent);
        }
    }
}
