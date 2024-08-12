/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See  {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.charts.model;

/*-
 * #%L
 * Vaadin Charts for Flow
 * %%
 * Copyright (C) 2014 - 2019 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <https://vaadin.com/license/cval-3>.
 * #L%
 */

/**
 * The position of the tick marks relative to the axis line. Can be one of
 * INSIDE and OUTSIDE. Defaults to OUTSIDE.
 */
public enum TickPosition implements ChartEnum {
    INSIDE("inside"), OUTSIDE("outside");

    private final String tickPosition;

    private TickPosition(String tickPosition) {
        this.tickPosition = tickPosition;
    }

    @Override
    public String toString() {
        return tickPosition;
    }
}
