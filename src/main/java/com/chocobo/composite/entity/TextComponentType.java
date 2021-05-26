package com.chocobo.composite.entity;

public enum TextComponentType {

    TEXT,
    PARAGRAPH("\r\n    "),
    SENTENCE(" "),
    LEXEME(" "),
    WORD,
    PUNCTUATION,
    LETTER;

    private String postfix = "";

    TextComponentType() { }

    TextComponentType(String postfix) {
        this.postfix = postfix;
    }

    public String getPostfix() {
        return postfix;
    }
}
