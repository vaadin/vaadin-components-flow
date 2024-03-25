/**
 * Copyright (C) 2000-2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.textfield.testbench;

import java.util.Collections;

import com.vaadin.testbench.TestBenchElement;

class TextFieldElementHelper {
    static void setValue(TestBenchElement element, String value) {
        element.setProperty("value", value);
        element.dispatchEvent("input",
                Collections.singletonMap("bubbles", true));
        element.dispatchEvent("change",
                Collections.singletonMap("bubbles", true));
    }
}
