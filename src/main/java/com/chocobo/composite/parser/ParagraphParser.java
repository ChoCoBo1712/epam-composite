package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComposite;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;

public class ParagraphParser extends AbstractTextParser{

    private static final String PARAGRAPH_SPLITTER_REGEX = "[\\n\\t]+";

    @Override
    public void parse(AbstractTextComponent component, String data) throws CompositeException {
        String[] paragraphs = data.split(PARAGRAPH_SPLITTER_REGEX);

        for (String paragraph : paragraphs) {
            TextComposite paragraphComponent = new TextComposite(TextComponentType.PARAGRAPH);
            component.add(paragraphComponent);
            nextParser.parse(paragraphComponent, paragraph);
        }
    }
}
