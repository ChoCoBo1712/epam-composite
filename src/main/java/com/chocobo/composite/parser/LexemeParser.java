package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;

public class LexemeParser extends AbstractTextParser {

    private static final String LEXEME_SPLITTER_REGEX = "\\s";

    @Override
    public void parse(AbstractTextComponent component, String data) throws CompositeException {
        String[] lexemes = data.split(LEXEME_SPLITTER_REGEX);

        for (String lexeme : lexemes) {
            TextComposite lexemeComponent = new TextComposite(TextComponentType.LEXEME);
            component.add(lexemeComponent);
            nextParser.parse(lexemeComponent, lexeme);
        }
    }
}
