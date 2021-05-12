package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;

public class SentenceParser extends AbstractTextParser {

    private static final String SENTENCE_SPLITTER_REGEX = "(?<=\\.|\\?|!|\\.{3})\\s";

    @Override
    public void parse(AbstractTextComponent component, String data) throws CompositeException {
        String[] sentences = data.split(SENTENCE_SPLITTER_REGEX);

        for (String sentence : sentences) {
            TextComposite sentenceComponent = new TextComposite(TextComponentType.SENTENCE);
            component.add(sentenceComponent);
            nextParser.parse(sentenceComponent, sentence);
        }
    }
}
