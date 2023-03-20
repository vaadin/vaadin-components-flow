/**
 * Copyright (C) 2000-2023 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.charts.events;

/*-
 * #%L
 * Vaadin Charts for Flow
 * %%
 * Copyright (C) 2014 - 2020 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Developer License
 * 4.0 (CVDLv4).
 * 
 * For the full License, see <https://vaadin.com/license/cvdl-4.0>.
 * #L%
 */

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.charts.Chart;

/**
 * Fired before a chart is printed using the print menu
 */
@DomEvent("chart-before-print")
public class ChartBeforePrintEvent extends ComponentEvent<Chart> {

    /**
     * Constructs a ChartBeforePrintEvent
     *
     * @param source
     * @param fromClient
     */
    public ChartBeforePrintEvent(Chart source, boolean fromClient) {
        super(source, fromClient);
    }
}
