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
package com.vaadin.flow.component.checkbox.tests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route("vaadin-checkbox/refresh-data-provider")
public class RefreshDataProviderPage extends Div {

    public RefreshDataProviderPage() {
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setLabel("Label");
        group.setId("group");

        List<String> items = new LinkedList<>(Arrays.asList("foo", "bar"));
        group.setItems(new ListDataProvider<>(items));

        Div valueDiv = new Div();
        valueDiv.setId("value-div");
        group.addValueChangeListener(e -> valueDiv.setText(e.getValue().stream()
                .sorted(String::compareTo).collect(Collectors.joining(", "))));

        NativeButton refreshAllButton = new NativeButton("Refresh all items",
                e -> {
                    group.getListDataView().refreshAll();
                });
        refreshAllButton.setId("refresh-all-items");

        NativeButton updateItemsButton = new NativeButton("Update items", e -> {
            items.add("baz");
            items.remove(0);
            group.getListDataView().refreshAll();
        });
        updateItemsButton.setId("update-items");

        NativeButton updateItemLabelGeneratorButton = new NativeButton(
                "Update labels", e -> {
                    group.setItemLabelGenerator(item -> item + " (Updated)");
                });
        updateItemLabelGeneratorButton.setId("update-labels");

        add(group, refreshAllButton, updateItemsButton,
                updateItemLabelGeneratorButton, valueDiv);
    }
}
