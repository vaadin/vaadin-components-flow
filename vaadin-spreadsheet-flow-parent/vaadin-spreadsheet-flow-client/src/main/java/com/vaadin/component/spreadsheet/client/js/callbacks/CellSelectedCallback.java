/**
 * Copyright 2000-2025 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>} for the full
 * license.
 */
package com.vaadin.component.spreadsheet.client.js.callbacks;

@FunctionalInterface
public interface CellSelectedCallback {

    void apply(int row, int column, boolean oldSelectionRangeDiscarded);

}
