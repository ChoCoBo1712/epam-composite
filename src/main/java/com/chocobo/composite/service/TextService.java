package com.chocobo.composite.service;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.exception.CompositeException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface TextService {

    List<AbstractTextComponent> sortParagraphs(
            AbstractTextComponent text, Comparator<AbstractTextComponent> comparator
    ) throws CompositeException;

    List<AbstractTextComponent> findSentencesWithLongestWord(AbstractTextComponent text) throws CompositeException;

    AbstractTextComponent removeSentencesWithWordsAmountLessThan(
            AbstractTextComponent text, int wordsAmount
    ) throws CompositeException;

    Map<String, Integer> countIdenticalWords(AbstractTextComponent text) throws CompositeException;

    long countVowelsInSentence(AbstractTextComponent sentence) throws CompositeException;

    long countConsonantsInSentence(AbstractTextComponent sentence) throws CompositeException;
}
