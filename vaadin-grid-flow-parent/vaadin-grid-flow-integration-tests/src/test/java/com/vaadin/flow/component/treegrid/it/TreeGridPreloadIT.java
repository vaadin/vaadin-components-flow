/*
 * Copyright 2000-2022 Vaadin Ltd.
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
package com.vaadin.flow.component.treegrid.it;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.flow.testutil.TestPath;

@TestPath("vaadin-grid/treegrid-preload")
public class TreeGridPreloadIT extends AbstractTreeGridIT {

    private final int EAGER_FETCH_VIEWPORT_SIZE_ESTIMATE = 40;
    private TextFieldElement requestCount;
    private ButtonElement requestCountReset;
    private TextAreaElement receivedParents;

    private void open(Integer expandedRootIndex) {
        String url = getRootURL() + getTestPath();
        if (expandedRootIndex != null) {
            url += "/" + expandedRootIndex;
        }
        getDriver().get(url);
        waitForDevServer();

        setupTreeGrid();
        requestCount = $(TextFieldElement.class).id("request-count");
        requestCountReset = $(ButtonElement.class).id("request-count-reset");
        receivedParents = $(TextAreaElement.class).id("received-parents");
    }

    private boolean parentItemsReceived(String parentId) {
        return Arrays.stream(receivedParents.getValue().split("\n"))
                .anyMatch(parentId::equals);
    }

    @Test
    public void firstExpanded_shouldHaveItemRecursivelyExpanded() {
        open(0);
        verifyRow(0, "/0/0");
        verifyRow(4, "/0/0/1/0/2/0/3/0/4/0");
    }

    @Test
    public void firstExpanded_shouldPreLoadDataForExpandedChildren() {
        open(0);
        Assert.assertEquals("1", requestCount.getValue());
    }

    @Test
    public void firstExpanded_shouldNotHaveDataForExpandedRootItemsOutsideViewportEstimate() {
        open(0);
        Assert.assertFalse(parentItemsReceived("/0/1"));
    }

    @Test
    public void firstExpanded_shouldNotHaveDataForExpandedChildrenOutsideViewportEstimate() {
        open(0);
        Assert.assertFalse(parentItemsReceived("/0/0/1/1"));
    }

    @Test
    public void firstExpanded_scrollByViewportEstimate_shouldHaveItemRecursivelyExpanded() {
        open(0);
        getTreeGrid().scrollToRow(EAGER_FETCH_VIEWPORT_SIZE_ESTIMATE);
        verifyRow(EAGER_FETCH_VIEWPORT_SIZE_ESTIMATE + 4,
                "/0/0/1/1/2/0/3/0/4/0");
    }

    @Test
    public void firstExpanded_scrollByViewportEstimate_shouldPreLoadDataForExpandedChildren() {
        open(0);
        requestCountReset.click();

        getTreeGrid().scrollToRow(EAGER_FETCH_VIEWPORT_SIZE_ESTIMATE);
        Assert.assertEquals("1", requestCount.getValue());
    }

    @Test
    public void firstExpanded_reExpand_shouldPreLoadDataForExpandedChildren() {
        open(0);
        requestCountReset.click();

        getTreeGrid().collapseWithClick(0);
        getTreeGrid().expandWithClick(0);
        // Expanding a recursively expanded parent doesn't yet trigger a preload
        // so
        // a second request is made from client.
        Assert.assertEquals("2", requestCount.getValue());
    }

    @Test
    public void firstExpanded_reExpand_shouldHaveItemRecursivelyExpanded() {
        open(0);
        getTreeGrid().collapseWithClick(0);
        getTreeGrid().expandWithClick(0);
        verifyRow(0, "/0/0");
        verifyRow(4, "/0/0/1/0/2/0/3/0/4/0");
    }

    @Test
    public void firstExpanded_reExpandChild_shouldPreLoadDataForExpandedChildren() {
        open(0);
        requestCountReset.click();

        getTreeGrid().collapseWithClick(2);
        getTreeGrid().expandWithClick(2);
        Assert.assertEquals("1", requestCount.getValue());
    }

    @Test
    public void firstExpanded_reExpandChild_shouldHaveItemRecursivelyExpanded() {
        open(0);
        getTreeGrid().collapseWithClick(1);
        getTreeGrid().expandWithClick(1);
        verifyRow(0, "/0/0");
        verifyRow(4, "/0/0/1/0/2/0/3/0/4/0");
    }

    @Test
    public void secondExpanded_shouldNotHaveDataForNonExpandedRootItems() {
        open(1);
        Assert.assertTrue(parentItemsReceived("/0/1"));
        Assert.assertFalse(parentItemsReceived("/0/0"));
    }

    private void verifyRow(int rowActualIndex, String itemId) {
        Assert.assertEquals("Invalid id at index " + rowActualIndex, itemId,
                getTreeGrid().getCell(rowActualIndex, 0).getText());
    }
}
