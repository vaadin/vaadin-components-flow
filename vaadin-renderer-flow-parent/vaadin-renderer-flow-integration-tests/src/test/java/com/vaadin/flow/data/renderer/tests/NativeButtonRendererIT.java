/*
 * Copyright 2000-2023 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.data.renderer.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.testutil.TestPath;
import com.vaadin.tests.AbstractComponentIT;

@TestPath("vaadin-renderer-flow/native-button-renderer")
public class NativeButtonRendererIT extends AbstractComponentIT {

    @Before
    public void init() {
        open();
    }

    @Test
    public void shouldRenderAllNativeButtons() {
        WebElement element = findElement(By.id("nativeBtn"));
        Assert.assertNotNull(element);
        Assert.assertEquals("button", element.getTagName());
        Assert.assertEquals("Label Item", element.getText());
    }
}
