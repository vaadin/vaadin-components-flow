package com.vaadin.flow.component.spreadsheet.tests.charts.typetests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.component.spreadsheet.tests.charts.ChartTestBase;

public class PieAndDonutTest extends ChartTestBase {

    protected Double[][] pieData = { { 100d, 200d, 2000d, 800d, 99d } };

    @Before
    public void init() {
        var ui = new UI();
        UI.setCurrent(ui);
    }

    @Test
    public void donutChart() throws Exception {
        Configuration conf = getChartFromSampleFile(
                "TypeSample - Pie and Donut.xlsx", "A8").getConfiguration();

        assertSeriesType(conf.getSeries(), ChartType.PIE);
        assertData(conf.getSeries(), data);

        // test all rings visible

        // test hole is of correct size
    }

    @Test
    public void pieNormalChart() throws Exception {
        Configuration conf = getChartFromSampleFile(
                "TypeSample - Pie and Donut.xlsx", "F1").getConfiguration();

        assertSeriesType(conf.getSeries(), ChartType.PIE);
        assertData(conf.getSeries(), pieData);
    }

    @Test
    public void pie3dChart() throws Exception {
        Configuration conf = getChartFromSampleFile(
                "TypeSample - Pie and Donut.xlsx", "K1").getConfiguration();

        assertSeriesType(conf.getSeries(), ChartType.PIE);
        assertData(conf.getSeries(), pieData);
        assert3dEnabled(conf);
    }

    @Test
    public void pieExplodedChart() throws Exception {
        Configuration conf = getChartFromSampleFile(
                "TypeSample - Pie and Donut.xlsx", "F17").getConfiguration();

        assertSeriesType(conf.getSeries(), ChartType.PIE);
        assertData(conf.getSeries(), pieData);
        assertSlicedItems(conf.getSeries());
    }

    @Test
    public void pieExploded3dChart() throws Exception {
        Configuration conf = getChartFromSampleFile(
                "TypeSample - Pie and Donut.xlsx", "K17").getConfiguration();

        assertSeriesType(conf.getSeries(), ChartType.PIE);
        assertData(conf.getSeries(), pieData);
        assert3dEnabled(conf);
        assertSlicedItems(conf.getSeries());
    }

    private void assertSlicedItems(List<Series> series) {
        for (Series s : series) {
            for (DataSeriesItem i : ((DataSeries) s).getData()) {
                Assert.assertTrue("Item was not sliced in an exploded pie",
                        i.getSliced());
            }
        }
    }
}
