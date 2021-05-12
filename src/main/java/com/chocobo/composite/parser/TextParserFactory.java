package com.chocobo.composite.parser;

public class TextParserFactory {

    private TextParserFactory() {}

    public static AbstractTextParser createParser() {
        LetterParser letterParser = new LetterParser();
        WordAndPunctuationParser wordAndPunctuationParser = new WordAndPunctuationParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();
        ParagraphParser paragraphParser = new ParagraphParser();

        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordAndPunctuationParser);
        wordAndPunctuationParser.setNextParser(letterParser);

        return paragraphParser;
    }
}
