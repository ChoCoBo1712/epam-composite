package com.chocobo.composite.service.impl;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;
import com.chocobo.composite.service.TextService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {

    @Override
    public List<AbstractTextComponent> sortParagraphs(
            AbstractTextComponent text, Comparator<AbstractTextComponent> comparator
    ) throws CompositeException {
        if (text == null) {
            throw new CompositeException("Got null component");
        }
        if (text.getType() != TextComponentType.TEXT) {
            throw new CompositeException("Wrong text component type. Expected TEXT, got " + text.getType());
        }

        List<AbstractTextComponent> textComponents = text.getChildren();
        return textComponents.stream()
                .filter(component -> component.getType() == TextComponentType.PARAGRAPH)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
