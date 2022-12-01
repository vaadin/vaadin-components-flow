package com.vaadin.flow.component.charts.events;

/**
 * Copyright (C) 2000-2022 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.charts.Chart;

/**
 * The PointSelectEvent class stores data for select events on the points of the
 * chart.
 */
@DomEvent("point-select")
public class PointSelectEvent extends ComponentEvent<Chart> implements HasItem {

    private final int seriesIndex;
    private final String category;
    private final int pointIndex;
    private final String pointId;

    public PointSelectEvent(Chart source, boolean fromClient,
            @EventData("event.detail.originalEvent.target.series.index") int seriesIndex,
            @EventData("event.detail.originalEvent.target.category") String category,
            @EventData("event.detail.originalEvent.target.index") int pointIndex,
            @EventData("event.detail.originalEvent.target.id") String pointId) {
        super(source, fromClient);
        this.seriesIndex = seriesIndex;
        this.category = category;
        this.pointIndex = pointIndex;
        this.pointId = pointId;
    }

    @Override
    public int getSeriesItemIndex() {
        return seriesIndex;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public int getItemIndex() {
        return pointIndex;
    }

    @Override
    public String getItemId() {
        return pointId;
    }
}
