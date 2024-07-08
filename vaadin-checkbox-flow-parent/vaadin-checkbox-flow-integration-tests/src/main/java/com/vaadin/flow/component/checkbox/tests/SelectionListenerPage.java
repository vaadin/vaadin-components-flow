/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.checkbox.tests;

import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("vaadin-checkbox/selection-listener")
public class SelectionListenerPage extends Div {

    public SelectionListenerPage() {
        Div selectedInfo = new Div();
        selectedInfo.setId("current-selection");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems("foo", "bar");

        checkboxGroup.addSelectionListener(event -> {
            Set<String> addedSelection = event.getNewSelection();
            String selected = addedSelection.stream().sorted()
                    .collect(Collectors.joining(", "));
            selectedInfo.setText(selected);
        });

        add(checkboxGroup, selectedInfo);
    }
}
