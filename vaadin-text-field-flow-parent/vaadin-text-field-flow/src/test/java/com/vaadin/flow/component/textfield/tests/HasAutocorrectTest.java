/*
 * Copyright 2000-2025 Vaadin Ltd.
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
package com.vaadin.flow.component.textfield.tests;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.textfield.HasAutocorrect;

public class HasAutocorrectTest {

    @Tag("div")
    public static class HasAutocorrectComponent extends Component
            implements HasAutocorrect {

    }

    @Test
    public void defaultValue() {
        HasAutocorrectComponent c = new HasAutocorrectComponent();
        Assert.assertFalse(c.isAutocorrect());
    }

    @Test
    public void enableAutocorrect() {
        HasAutocorrectComponent c = new HasAutocorrectComponent();
        c.setAutocorrect(true);
        Assert.assertTrue(c.isAutocorrect());
    }

    @Test
    public void disableAutocorrect() {
        HasAutocorrectComponent c = new HasAutocorrectComponent();
        c.setAutocorrect(true);
        c.setAutocorrect(false);
    }
}
