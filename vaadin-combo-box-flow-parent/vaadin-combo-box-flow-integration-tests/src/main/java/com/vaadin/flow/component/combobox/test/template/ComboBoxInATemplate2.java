/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.combobox.test.template;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("combo-box-in-a-template2")
@JsModule("./src/combo-box-in-a-template2.js")
public class ComboBoxInATemplate2 extends PolymerTemplate<TemplateModel> {

    @Id("comboBox2")
    ComboBox<String> comboBox;

    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}
