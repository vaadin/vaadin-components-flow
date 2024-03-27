/**
 * Copyright (C) 2000-2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.grid;

import java.util.Collection;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.AbstractRow.AbstractCell;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;

/**
 * One row of {@link FooterCell}s in a Grid.
 *
 * @author Vaadin Ltd.
 */
public class FooterRow extends AbstractRow<FooterCell> {

    /**
     * A footer cell in a Grid.
     *
     * @author Vaadin Ltd.
     */
    public static class FooterCell extends AbstractCell {

        FooterCell(AbstractColumn<?> column) {
            super(column);
            if (column.getFooterRenderer() == null) {
                column.setFooterText("");
            }
        }

        @Override
        public String getText() {
            return getColumn().getFooterText();
        }

        @Override
        public void setText(String text) {
            getColumn().setFooterText(text);
        }

        @Override
        public Component getComponent() {
            return getColumn().getFooterComponent();
        }

        @Override
        public void setComponent(Component component) {
            getColumn().setFooterComponent(component);
        }

    }

    /**
     * Creates a new footer row from the layer of column elements.
     *
     * @param layer
     */
    FooterRow(ColumnLayer layer) {
        super(layer, FooterCell::new);
    }

    @Override
    public FooterCell join(Collection<FooterCell> cells) {
        if (layer.getGrid().getColumnLayers().indexOf(layer) == 0) {
            throw new UnsupportedOperationException(
                    "Cells cannot be joined on the top-most footer row. "
                            + "This row is used as the default row for setting column "
                            + "footers, so each cell in it should have maximum one "
                            + "related column.");
        }
        return super.join(cells);
    }

    @Override
    protected boolean isOutmostRow() {
        List<ColumnLayer> layers = layer.getGrid().getColumnLayers();

        for (int i = layers.size() - 1; i >= 0; i--) {
            ColumnLayer layer = layers.get(i);
            if (layer.isFooterRow()) {
                return equals(layer.asFooterRow());
            }
        }
        return false;
    }
}
