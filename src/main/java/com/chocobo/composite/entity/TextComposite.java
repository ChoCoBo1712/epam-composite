package com.chocobo.composite.entity;

import java.util.ArrayList;
import java.util.List;

public class TextComposite extends AbstractTextComponent {
    private final List<AbstractTextComponent> components = new ArrayList<>();

    public TextComposite(TextComponentType type) {
        super(type);
    }

    @Override
    public void add(AbstractTextComponent component) {
        components.add(component);
    }

    @Override
    public void remove(AbstractTextComponent component) {
        components.remove(component);
    }

    @Override
    public AbstractTextComponent getChild(int index) {
        return components.get(index).clone();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        components.forEach(component -> {
            TextComponentType type = component.getType();
            String prefix = type.getPrefix();
            String suffix = type.getPostfix();
            stringBuilder.append(prefix).append(component).append(suffix);
        });

        return (getType() == TextComponentType.TEXT)
                ? stringBuilder.toString().stripTrailing()
                : stringBuilder.toString().strip();
    }
}
