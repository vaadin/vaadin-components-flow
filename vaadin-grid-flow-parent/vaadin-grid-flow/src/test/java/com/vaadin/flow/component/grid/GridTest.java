
package com.vaadin.flow.component.grid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.data.provider.DataCommunicatorTest;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.tests.DataProviderListenersTest;

public class GridTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void templateWarningSuppressed() {
        Grid<String> grid = new Grid<>();

        Assert.assertTrue("Template warning is not suppressed",
                grid.getElement().hasAttribute("suppress-template-warning"));
    }

    @Test
    public void dataViewForFaultyDataProvider_throwsException() {
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage(
                "GridListDataView only supports 'ListDataProvider' "
                        + "or it's subclasses, but was given a "
                        + "'AbstractBackEndDataProvider'");

        Grid<String> grid = new Grid<>();
        final GridListDataView<String> listDataView = grid
                .setItems(Arrays.asList("one", "two"));

        DataProvider<String, Void> dataProvider = DataProvider.fromCallbacks(
                query -> Arrays.asList("one").stream(), query -> 1);

        grid.setDataProvider(dataProvider);

        grid.getListDataView();
    }

    @Test
    public void selectItem_lazyDataSet_selectionWorks() {
        final Grid<String> grid = new Grid<>();
        grid.setItems(query -> Stream.of("foo", "bar"));
        grid.select("foo");
        Assert.assertEquals(1, grid.getSelectedItems().size());
        Assert.assertTrue(grid.getSelectedItems().contains("foo"));
    }

    @Test
    public void setHeightByRows_allRowsAreVisible() {
        final Grid<String> grid = new Grid<>();

        Assert.assertEquals(null,
                grid.getElement().getProperty("allRowsVisible"));

        grid.setHeightByRows(true);
        Assert.assertEquals("true",
                grid.getElement().getProperty("allRowsVisible"));
    }

    @Test
    public void setAllRowsVisible_allRowsAreVisible() {
        final Grid<String> grid = new Grid<>();

        Assert.assertEquals(null,
                grid.getElement().getProperty("allRowsVisible"));

        grid.setAllRowsVisible(true);
        Assert.assertEquals("true",
                grid.getElement().getProperty("allRowsVisible"));
    }

    @Test
    public void setAllRowsVisibleProperty_isHeightByRowsAndIsAllRowsVisibleWork() {
        final Grid<String> grid = new Grid<>();
        grid.getElement().setProperty("allRowsVisible", true);

        Assert.assertTrue(grid.isHeightByRows());
        Assert.assertTrue(grid.isAllRowsVisible());
    }

    @Test
    public void dataProviderListeners_gridAttachedAndDetached_oldDataProviderListenerRemoved() {
        DataProviderListenersTest
                .checkOldListenersRemovedOnComponentAttachAndDetach(
                        new Grid<>(), 2, 2, new int[] { 0, 2 },
                        new DataCommunicatorTest.MockUI());
    }

    @Test
    public void setSmallPageSize_callSetRequestedRangeWithLengthLargerThan500_doesNotThrowException() {
        final Grid<String> grid = new Grid<>();

        grid.setPageSize(10);
        callSetRequestedRange(grid, 0, 600);
    }

    @Test
    public void setAllRowsVisible_setLargePageSize_callSetRequestedRangeWithLengthLargerThan500_doesNotThrowException() {
        final Grid<String> grid = new Grid<>();

        grid.setPageSize(100);
        grid.setAllRowsVisible(true);
        callSetRequestedRange(grid, 0, 600);
    }

    @Test
    public void setAllRowsVisible_setSmallPageSize_callSetRequestedRangeWithLengthSmallerThan500_doesNotThrowException() {
        final Grid<String> grid = new Grid<>();

        grid.setPageSize(10);
        grid.setAllRowsVisible(true);
        callSetRequestedRange(grid, 0, 400);
    }

    @Test
    public void setAllRowsVisible_setSmallPageSize_callSetRequestedRangeWithLengthLargerThan500_throwsException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(
                "Attempted to fetch more items from server than allowed in one go");

        final Grid<String> grid = new Grid<>();

        grid.setPageSize(10);
        grid.setAllRowsVisible(true);
        callSetRequestedRange(grid, 0, 600);
    }

    private void callSetRequestedRange(Grid<String> grid, int start,
            int length) {
        try {
            Method method = Grid.class.getDeclaredMethod("setRequestedRange",
                    int.class, int.class);
            method.setAccessible(true);
            method.invoke(grid, start, length);
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e.getCause();
            }
            Assert.fail("Could not call Grid.setRequestedRange");
        }
    }
}
