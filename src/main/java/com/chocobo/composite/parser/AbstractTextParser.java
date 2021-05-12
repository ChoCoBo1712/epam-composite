package com.chocobo.composite.parser;

import com.chocobo.composite.entity.AbstractTextComponent;
import com.chocobo.composite.exception.CompositeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractTextParser {

    protected AbstractTextParser nextParser = DefaultTextParser.getParser();

    public void setNextParser(AbstractTextParser textParser) {
        this.nextParser = textParser;
    }

    public abstract void parse(AbstractTextComponent component, String data) throws CompositeException;

    private static class DefaultTextParser extends AbstractTextParser {
        private static final Logger logger = LogManager.getLogger();
        private static final DefaultTextParser parser = new DefaultTextParser();

        public static DefaultTextParser getParser() {
            return parser;
        }

        @Override
        public void parse(AbstractTextComponent component, String data) {
            logger.info("End of parser chain");
        }
    }
}
