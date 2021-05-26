package com.chocobo.composite.service.impl;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.entity.TextComponentType;
import com.chocobo.composite.exception.CompositeException;
import com.chocobo.composite.service.TextService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {

    private static final String VOWEL_REGEX_PATTERN = "[aAeEiIoOuU]";

    @Override
    public List<AbstractTextComponent> sortParagraphs(
            AbstractTextComponent text, Comparator<AbstractTextComponent> comparator
    ) throws CompositeException {
        if (text == null || comparator == null) {
            throw new CompositeException("Got null parameter");
        }
        if (text.getType() != TextComponentType.TEXT) {
            throw new CompositeException("Wrong text component type. Expected TEXT, got " + text.getType());
        }

        List<AbstractTextComponent> textComponents = text.getChildren();
        return textComponents.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbstractTextComponent> findSentencesWithLongestWord(AbstractTextComponent text) throws CompositeException {
        if (text == null) {
            throw new CompositeException("Got null parameter");
        }
        if (text.getType() != TextComponentType.TEXT) {
            throw new CompositeException("Wrong text component type. Expected TEXT, got " + text.getType());
        }

        List<AbstractTextComponent> textComponents = text.getChildren();
        int maxWordLength = textComponents.stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .flatMap(sentence -> sentence.getChildren().stream())
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(word -> word.getType() == TextComponentType.WORD)
                .map(word -> word.getChildren().size())
                .max(Comparator.comparingInt(size -> size))
                .orElseThrow(() -> new CompositeException("Unable to find max word length in text: " + text));

        return textComponents.stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .filter(sentence -> sentenceHasWordWithLength(maxWordLength, sentence))
                .collect(Collectors.toList());
    }

    @Override
    public AbstractTextComponent removeSentencesWithWordsAmountLessThan(
            AbstractTextComponent text, int wordsAmount
    ) throws CompositeException {
        if (text == null) {
            throw new CompositeException("Got null parameter");
        }
        if (text.getType() != TextComponentType.TEXT) {
            throw new CompositeException("Wrong text component type. Expected TEXT, got " + text.getType());
        }

        AbstractTextComponent newText = text.clone();
        List<AbstractTextComponent> textComponents = newText.getChildren();
        List<AbstractTextComponent> sentencesToRemove = textComponents.stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .filter(sentence -> sentenceHasLessWordsThan(wordsAmount, sentence))
                .collect(Collectors.toList());


        textComponents.forEach(paragraph -> paragraph.getChildren().stream()
                .filter(sentencesToRemove::contains)
                .forEach(paragraph::remove));

        return newText;
    }

    @Override
    public Map<String, Integer> countIdenticalWords(AbstractTextComponent text) throws CompositeException {
        if (text == null) {
            throw new CompositeException("Got null parameter");
        }
        if (text.getType() != TextComponentType.TEXT) {
            throw new CompositeException("Wrong text component type. Expected TEXT, got " + text.getType());
        }

        Map<String, Integer> map = new HashMap<>();
        List<AbstractTextComponent> textComponents = text.getChildren();
        textComponents.stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .flatMap(sentence -> sentence.getChildren().stream())
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(word -> word.getType() == TextComponentType.WORD)
                .forEach(word -> {
                    String key = word.toString();
                    int count = map.getOrDefault(key, 0);
                    map.put(key, ++count);
                });

        return map;
    }

    @Override
    public long countVowelsInSentence(AbstractTextComponent sentence) throws CompositeException {
        if (sentence == null) {
            throw new CompositeException("Got null parameter");
        }
        if (sentence.getType() != TextComponentType.SENTENCE) {
            throw new CompositeException("Wrong text component type. Expected SENTENCE, got " + sentence.getType());
        }

        List<AbstractTextComponent> sentenceComponents = sentence.getChildren();
        return sentenceComponents.stream()
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(word -> word.getType() == TextComponentType.WORD)
                .filter(letter -> Pattern.matches(VOWEL_REGEX_PATTERN, letter.toString()))
                .count();
    }

    @Override
    public long countConsonantsInSentence(AbstractTextComponent sentence) throws CompositeException {
        if (sentence == null) {
            throw new CompositeException("Got null parameter");
        }
        if (sentence.getType() != TextComponentType.SENTENCE) {
            throw new CompositeException("Wrong text component type. Expected SENTENCE, got " + sentence.getType());
        }

        List<AbstractTextComponent> sentenceComponents = sentence.getChildren();
        return sentenceComponents.stream()
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(word -> word.getType() == TextComponentType.WORD)
                .filter(letter -> !Pattern.matches(VOWEL_REGEX_PATTERN, letter.toString()))
                .count();
    }

    private boolean sentenceHasLessWordsThan(int wordsAmount, AbstractTextComponent sentence) {
        return sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(child -> child.getType() == TextComponentType.WORD)
                .count() < wordsAmount;
    }

    private boolean sentenceHasWordWithLength(int maxWordLength, AbstractTextComponent sentence) {
        return sentence.getChildren().stream()
                .flatMap(lexeme -> lexeme.getChildren().stream())
                .filter(child -> child.getType() == TextComponentType.WORD)
                .anyMatch(word -> word.getChildren().size() == maxWordLength);
    }
}
