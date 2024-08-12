/*
 * Copyright 2000-2024 Vaadin Ltd.
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
package com.vaadin.flow.component.radiobutton.tests;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.testutil.TestPath;
import com.vaadin.tests.AbstractComponentIT;

@TestPath("vaadin-radio-button/refresh-on-value-change")
public class RefreshOnValueChangeIT extends AbstractComponentIT {

    @Test
    public void subsequentValueChangesDontAffectElementCount() {
        open();
        Assert.assertEquals(2, $("vaadin-radio-button").all().size());

        $("vaadin-radio-button").first().click();

        Assert.assertEquals(2, $("vaadin-radio-button").all().size());
    }
}
