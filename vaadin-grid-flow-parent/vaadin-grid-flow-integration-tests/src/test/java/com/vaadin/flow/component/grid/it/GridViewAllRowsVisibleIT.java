/*
 * Copyright 2000-2023 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.grid.it;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.flow.component.grid.testbench.GridTRElement;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.tests.AbstractComponentIT;

/**
 * Tests for ensuring Grid's row visibility configuration and utility methods to
 * fetch rows, and cells from specific rows and columns work as expected.
 */
@TestPath("vaadin-grid-it-demo/all-rows-visible")
public class GridViewAllRowsVisibleIT extends AbstractComponentIT {

    private GridElement grid;

    @Before
    public void init() {
        open();
        grid = $(GridElement.class).id("grid-all-rows-visible");
        scrollToElement(grid);
        waitUntil(driver -> grid.getRowCount() == 50);
    }

    @Test
    public void allRowsVisible_allRowsAreFetched() {
        Assert.assertEquals("Grid should have allRowsVisible set to true",
                "true", grid.getAttribute("allRowsVisible"));
    }

    @Test
    public void cellsRowIndexUtilMethod_returnsExpectedCells() {
        int firstVisibleRowIndex = grid.getFirstVisibleRowIndex();
        for (GridTHTDElement cell : grid.getCells(firstVisibleRowIndex)) {
            Assert.assertEquals(grid.getCell(cell.getRow(), cell.getColumn()),
                    cell);
        }
    }

    @Test
    public void cellsColumnElementsUtilMethod_returnsExpectedCells() {
        List<GridColumnElement> columns = grid.getAllColumns();
        int firstVisibleRowIndex = grid.getFirstVisibleRowIndex();
        for (int col = 0; col < columns.size(); col++) {
            for (GridTHTDElement cell : grid.getCells(firstVisibleRowIndex,
                    columns.get(col))) {
                Assert.assertEquals(
                        grid.getCell(cell.getRow(), cell.getColumn()), cell);
            }
        }
    }

    @Test
    public void indexesBasedRowsFetchingMethod_returnsExpectedRows() {
        int expectedRowAmount = grid.getRowCount();
        int actualRowAmount = grid.getRows(0, expectedRowAmount - 1).size();
        Assert.assertEquals(expectedRowAmount, actualRowAmount);
    }

    @Test
    public void indexesBasedRowsFetchingMethod_returnsSameElementAsSingularRowFetchingMethod() {
        List<GridTRElement> rows = grid.getRows(0, grid.getRowCount() - 1);
        for (int i = 0; i < rows.size(); i++) {
            Assert.assertEquals(grid.getRow(i), rows.get(i));
        }
    }

}
