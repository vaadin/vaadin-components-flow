package com.vaadin.flow.component.gridpro.testbench;

/**
 * Copyright (C) 2000-2022 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */

import com.vaadin.testbench.TestBenchElement;

/**
 * A TestBench element representing a <code>&lt;tr&gt;</code> element in a grid.
 */
public class GridTRElement extends TestBenchElement {

    /**
     * Gets the cell for the given column in this row.
     *
     * @param column
     *            the column element
     * @return the cell for the given column
     */
    public GridTHTDElement getCell(GridProColumnElement column) {
        TestBenchElement e = (TestBenchElement) executeScript(
                "const grid = arguments[0];" //
                        + "const columnId = arguments[1];" //
                        + "return Array.from(grid.children)."
                        + "filter(function(cell) { return cell._column && cell._column.__generatedTbId == columnId;})[0]",
                this, column.get__generatedId());
        return e == null ? null : e.wrap(GridTHTDElement.class);
    }

}
