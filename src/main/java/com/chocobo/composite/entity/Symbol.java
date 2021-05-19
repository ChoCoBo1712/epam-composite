package com.chocobo.composite.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    public List<AbstractTextComponent> getChildren(int index) {
        logger.error("getChildren() not supported in leaf");
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
