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
package com.vaadin.flow.component.sidenav.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.flow.component.sidenav.SideNavItem;

public class SideNavItemTest {

    private SideNavItem sideNavItem;

    @Before
    public void setup() {
        sideNavItem = new SideNavItem("Item", "/path");
    }

    @Test
    public void changeLabel_labelChanged() {
        Assert.assertEquals(sideNavItem.getLabel(), "Item");
        sideNavItem.setLabel("Item Changed");
        Assert.assertEquals(sideNavItem.getLabel(), "Item Changed");
    }

}
