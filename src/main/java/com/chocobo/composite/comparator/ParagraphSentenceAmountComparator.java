package com.chocobo.composite.comparator;

import com.chocobo.composite.entity.AbstractTextComponent;

import java.util.Comparator;

public class ParagraphSentenceAmountComparator implements Comparator<AbstractTextComponent> {

    @Override
    public int compare(AbstractTextComponent firstComponent, AbstractTextComponent secondComponent) {
        long firstComponentChildrenSize = firstComponent.getChildren().size();
        long secondComponentChildrenSize = secondComponent.getChildren().size();
        return Long.compare(firstComponentChildrenSize, secondComponentChildrenSize);
    }
}
