package com.vaadin.flow.component.charts;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Series;

/**
 * Tests for the {@link Configuration}
 *
 */
public class ConfigurationTest {

    @Test(expected = Test.None.class)
    public void configurationSetSeriesWithArraysAsListTest() {
        Configuration conf = new Configuration();

        conf.setSeries(Arrays.asList(new ListSeries()));
        conf.addSeries(new ListSeries());
    }

    @Test
    public void configurationSetSeriesWithListShouldMakeShallowCopyTest() {
        Configuration conf = new Configuration();

        List<Series> series = new ArrayList<>();
        conf.setSeries(series);
        
        series.add(new ListSeries());

        assertEquals(conf.getSeries().size(), 0);
    }
}
