/*
 * Copyright 2000-2020 Vaadin Ltd.
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

package com.vaadin.flow.component.combobox.test.dataview;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;

@Route("item-count-estimate/:estimate?([0-9]{1,9})")
public class ItemCountEstimateComboBoxPage
        extends AbstractItemCountComboBoxPage {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        super.beforeEnter(event);
        event.getRouteParameters().get("estimate")
                .ifPresent(string -> itemCountEstimateInput
                        .setValue(Integer.parseInt(string)));
    }
}
