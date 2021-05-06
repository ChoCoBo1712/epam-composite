package com.chocobo.composite.entity;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent {
    private final List<TextComponent> components = new ArrayList<>();
    private final TextComponentType type;

    public TextComposite(TextComponentType type) {
        this.type = type;
    }

    @Override
    public void add(TextComponent component) {
        components.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        components.remove(component);
    }
}
