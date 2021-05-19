package com.chocobo.composite.service;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.exception.CompositeException;

import java.util.List;

public interface TextService {

    List<AbstractTextComponent> sortParagraphsBySentenceAmount(AbstractTextComponent text) throws CompositeException;
}
