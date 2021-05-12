package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.Symbol;
import com.chocobo.composite.entity.TextComponentType;

public class LetterParser extends AbstractTextParser {

    @Override
    public void parse(AbstractTextComponent component, String data) {
        char[] symbols = data.toCharArray();

        for (char symbolValue : symbols) {
            Symbol symbol = new Symbol(String.valueOf(symbolValue), TextComponentType.LETTER);
            component.add(symbol);
        }
    }
}
