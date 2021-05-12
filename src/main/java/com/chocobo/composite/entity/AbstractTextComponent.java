package com.chocobo.composite.entity;

public abstract class AbstractTextComponent implements Cloneable {

    private final TextComponentType type;

    public AbstractTextComponent(TextComponentType type) {
        this.type = type;
    }

    @Override
    public final AbstractTextComponent clone(){
        AbstractTextComponent textComponent = null;
        try {
            textComponent = (AbstractTextComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return textComponent;
    }

    public TextComponentType getType() {
        return type;
    }

    public abstract void add(AbstractTextComponent component);
    public abstract void remove(AbstractTextComponent component);
    public abstract AbstractTextComponent getChild(int index);
}
