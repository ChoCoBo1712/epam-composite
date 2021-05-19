package com.chocobo.composite.entity;

import java.util.List;

public abstract class AbstractTextComponent implements Cloneable {

    private final TextComponentType type;

    public AbstractTextComponent(TextComponentType type) {
        this.type = type;
    }

    public TextComponentType getType() {
        return type;
    }

    public abstract void add(AbstractTextComponent component);
    public abstract void remove(AbstractTextComponent component);
    public abstract List<AbstractTextComponent> getChildren();
}
