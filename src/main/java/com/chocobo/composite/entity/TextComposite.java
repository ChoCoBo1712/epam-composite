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
    public List<AbstractTextComponent> getChildren(int index) {
        return new ArrayList<>(components);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        components.forEach(component -> {
            TextComponentType type = component.getType();
            String postfix = type.getPostfix();
            stringBuilder.append(component).append(postfix);
        });

        TextComponentType componentType = getType();
        return (componentType == TextComponentType.TEXT || componentType == TextComponentType.PARAGRAPH)
                ? stringBuilder.toString().stripTrailing()
                : stringBuilder.toString();
    }
}
