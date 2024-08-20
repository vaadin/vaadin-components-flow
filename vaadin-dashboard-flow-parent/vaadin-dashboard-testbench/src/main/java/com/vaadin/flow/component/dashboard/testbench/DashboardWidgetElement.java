/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>} for the full
 * license.
 */
package com.vaadin.flow.component.dashboard.testbench;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

/**
 * @author Vaadin Ltd
 */
@Element("vaadin-dashboard-widget")
public class DashboardWidgetElement extends TestBenchElement {

    /**
     * Returns the title of the widget.
     *
     * @return the {@code widget-title} property from the web component
     */
    public String getTitle() {
        return getAttribute("widget-title");
    }
}
