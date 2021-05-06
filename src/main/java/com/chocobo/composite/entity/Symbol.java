package com.chocobo.composite.entity;

public class Symbol implements TextComponent {

    private final char value;

    public Symbol(char value) {
        this.value = value;
    }

    @Override
    public void add(TextComponent component) {

    }

    @Override
    public void remove(TextComponent component) {

    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
