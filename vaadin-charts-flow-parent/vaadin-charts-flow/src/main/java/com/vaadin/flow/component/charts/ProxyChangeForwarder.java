package com.vaadin.flow.component.charts;

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

import com.vaadin.flow.component.charts.events.internal.AbstractSeriesEvent;
import com.vaadin.flow.component.charts.events.internal.AxisRescaledEvent;
import com.vaadin.flow.component.charts.events.internal.ConfigurationChangeListener;
import com.vaadin.flow.component.charts.events.internal.DataAddedEvent;
import com.vaadin.flow.component.charts.events.internal.DataRemovedEvent;
import com.vaadin.flow.component.charts.events.internal.DataUpdatedEvent;
import com.vaadin.flow.component.charts.events.internal.ItemSlicedEvent;
import com.vaadin.flow.component.charts.events.internal.SeriesAddedEvent;
import com.vaadin.flow.component.charts.events.internal.SeriesChangedEvent;
import com.vaadin.flow.component.charts.events.internal.SeriesStateEvent;
import com.vaadin.flow.component.charts.model.AbstractConfigurationObject;
import com.vaadin.flow.component.charts.model.AxisDimension;
import com.vaadin.flow.component.charts.util.ChartSerialization;

class ProxyChangeForwarder implements ConfigurationChangeListener {

    private final Chart chart;

    ProxyChangeForwarder(Chart chart) {
        this.chart = chart;
    }

    @Override
    public void dataAdded(DataAddedEvent event) {
        if (event.getItem() != null) {
            chart.getElement().callFunction("__callSeriesFunction", "addPoint",
                    getSeriesIndex(event),
                    chart.getJsonFactory()
                            .parse(ChartSerialization.toJSON(event.getItem())),
                    true, event.isShift());
        }
    }

    @Override
    public void dataRemoved(DataRemovedEvent event) {
        chart.getElement().callFunction("__callPointFunction", "remove",
                getSeriesIndex(event), event.getIndex());
    }

    @Override
    public void dataUpdated(DataUpdatedEvent event) {
        if (event.getValue() != null) {
            chart.getElement().callFunction("__callPointFunction", "update",
                    getSeriesIndex(event), event.getPointIndex(),
                    event.getValue().doubleValue());
        } else {
            chart.getElement().callFunction("__callPointFunction", "update",
                    getSeriesIndex(event), event.getPointIndex(),
                    chart.getJsonFactory()
                            .parse(ChartSerialization.toJSON(event.getItem())));
        }
    }

    @Override
    public void seriesStateChanged(SeriesStateEvent event) {
        if (event.isEnabled()) {
            chart.getElement().callFunction("__callSeriesFunction", "show",
                    getSeriesIndex(event));
        } else {
            chart.getElement().callFunction("__callSeriesFunction", "hide",
                    getSeriesIndex(event));
        }
    }

    @Override
    public void axisRescaled(AxisRescaledEvent event) {
        chart.getElement().callFunction("__callAxisFunction", "setExtremes",
                event.getAxis(), event.getAxisIndex(),
                event.getMinimum().doubleValue(),
                event.getMaximum().doubleValue(), event.isRedrawingNeeded(),
                event.isAnimated());
    }

    @Override
    public void itemSliced(ItemSlicedEvent event) {
        chart.getElement().callFunction("__callPointFunction", "slice",
                getSeriesIndex(event), event.getIndex(), event.isSliced(),
                event.isRedraw(), event.isAnimation());
    }

    @Override
    public void seriesAdded(SeriesAddedEvent event) {
        chart.getElement().callFunction("__callChartFunction", "addSeries",
                chart.getJsonFactory().parse(ChartSerialization.toJSON(
                        (AbstractConfigurationObject) event.getSeries())));
    }

    @Override
    public void seriesChanged(SeriesChangedEvent event) {
        chart.getElement().callFunction("__callSeriesFunction", "update",
                getSeriesIndex(event),
                chart.getJsonFactory().parse(ChartSerialization.toJSON(
                        (AbstractConfigurationObject) event.getSeries())));
    }

    @Override
    public void resetZoom(boolean redraw, boolean animate) {
        for (int i = 0; i < chart.getConfiguration().getNumberOfxAxes(); i++) {
            chart.getElement().callFunction("__callAxisFunction", "setExtremes",
                    AxisDimension.X_AXIS.getIndex(), i, null, null, redraw,
                    animate);
        }
        for (int i = 0; i < chart.getConfiguration().getNumberOfyAxes(); i++) {
            chart.getElement().callFunction("__callAxisFunction", "setExtremes",
                    AxisDimension.Y_AXIS.getIndex(), i, null, null, redraw,
                    animate);
        }
    }

    private int getSeriesIndex(AbstractSeriesEvent event) {
        return chart.getConfiguration().getSeries().indexOf(event.getSeries());
    }

}
