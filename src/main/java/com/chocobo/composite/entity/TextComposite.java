package com.chocobo.composite.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TextComposite extends AbstractTextComponent {
    private List<AbstractTextComponent> components = new ArrayList<>();

    public TextComposite(TextComponentType type) {
        super(type);
    }

    @Override
    public final TextComposite clone() {
        TextComposite textComponent = (TextComposite) super.clone();
        textComponent.components = components.stream()
                .map(AbstractTextComponent::clone)
                .collect(Collectors.toList());
        return textComponent;
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
    public List<AbstractTextComponent> getChildren() {
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
        return (componentType == TextComponentType.TEXT)
                ? stringBuilder.insert(0, "    ").toString().stripTrailing()
                : stringBuilder.toString().strip();
    }
}
