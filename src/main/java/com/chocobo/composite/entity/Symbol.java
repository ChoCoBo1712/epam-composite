package com.chocobo.composite.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Symbol extends AbstractTextComponent {

    private static final Logger logger = LogManager.getLogger();
    private final String value;

    public Symbol(String value, TextComponentType type) {
        super(type);
        this.value = value;
    }

    @Override
    public void add(AbstractTextComponent component) {
        logger.error("add() not supported in leaf");
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(AbstractTextComponent component) {
        logger.error("remove() not supported in leaf");
        throw new UnsupportedOperationException();
    }

    @Override
    public AbstractTextComponent getChild(int index) {
        logger.error("getChild() not supported in leaf");
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
