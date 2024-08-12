/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See  {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.messages.testbench;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

/**
 * A TestBench element representing a <code>&lt;vaadin-message-input&gt;</code>
 * element.
 *
 * @author Vaadin Ltd.
 */
@Element("vaadin-message-input")
public class MessageInputElement extends TestBenchElement {

    /**
     * Submits a message.
     *
     * @param message
     *            the message
     */
    public void submit(String message) {
        setProperty("value", message);
        $("vaadin-message-input-button").first().click();
    }
}
