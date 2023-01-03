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
package com.vaadin.flow.component.combobox.test;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("vaadin-combo-box/readonly-blur")
public class ComboBoxReadOnlyBlurPage extends VerticalLayout {

    public ComboBoxReadOnlyBlurPage() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setAllowCustomValue(true);
        comboBox.setRequired(true);
        comboBox.setReadOnly(true);
        comboBox.addCustomValueSetListener(ev -> {
            Span span = new Span();
            span.setText("Custom value set");
            span.setId("custom-value-set");
            add(span);
        });
        comboBox.setItems("A", "B", "C");
        comboBox.setValue("D");
        comboBox.focus();

        add(comboBox);
    }

}
