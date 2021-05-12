package com.chocobo.composite.entity;

public enum TextComponentType {

    TEXT("", ""),
    PARAGRAPH("    ", "\r\n"),
    SENTENCE(" ", ""),
    LEXEME(" ", ""),
    WORD(" ", ""),
    PUNCTUATION("", ""),
    LETTER("", "");

    private final String prefix;
    private final String postfix;

    TextComponentType(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPostfix() {
        return postfix;
    }
}
