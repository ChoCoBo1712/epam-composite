package com.chocobo.composite.service;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.exception.CompositeException;

import java.util.Comparator;
import java.util.List;

public interface TextService {

    List<AbstractTextComponent> sortParagraphs(
            AbstractTextComponent text, Comparator<AbstractTextComponent> comparator
    ) throws CompositeException;
}
