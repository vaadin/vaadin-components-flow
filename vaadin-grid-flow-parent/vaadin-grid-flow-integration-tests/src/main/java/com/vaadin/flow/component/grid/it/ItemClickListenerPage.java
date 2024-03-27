/**
 * Copyright (C) 2000-2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.grid.it;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Route("vaadin-grid/item-click-listener")
public class ItemClickListenerPage extends Div {
    static final String GRID_FILTER_FOCUSABLE_HEADER = "grid-filter-focusable-header";

    public ItemClickListenerPage() {
        Div clickMsg = new Div();
        clickMsg.setId("clickMsg");

        Div dblClickMsg = new Div();
        dblClickMsg.setId("dblClickMsg");

        Div columnClickMsg = new Div();
        columnClickMsg.setId("columnClickMsg");

        Div columnDblClickMsg = new Div();
        columnDblClickMsg.setId("columnDblClickMsg");

        Grid<String> grid = new Grid<>();
        grid.setItems("foo", "bar");
        grid.addColumn(item -> item).setHeader("Name").setKey("Name");
        grid.addComponentColumn(Checkbox::new);

        grid.addItemClickListener(event -> {
            clickMsg.add(event.getItem());
            String key = event.getColumn().getKey();
            columnClickMsg.add(key == null ? "" : key);
        });

        grid.addItemDoubleClickListener(event -> {
            dblClickMsg.add(String.valueOf(event.getClientY()));
            String key = event.getColumn().getKey();
            columnDblClickMsg.add(key == null ? "" : key);
        });

        grid.setItemDetailsRenderer(new ComponentRenderer<>(
                (SerializableFunction<String, Span>) ItemClickListenerPage::getDetailsComponent));
        grid.setDetailsVisible("foo", false);
        grid.setDetailsVisible("bar", true);

        add(grid, clickMsg, dblClickMsg, columnClickMsg, columnDblClickMsg);

        createGridWithChangingKeysOfItemsAndFocusableHeader();
    }

    private static Span getDetailsComponent(String s) {
        Span result = new Span(s);
        result.setId("details-" + s);
        return result;
    }

    private void createGridWithChangingKeysOfItemsAndFocusableHeader() {
        Span message = new Span();
        message.setId("item-click-event-log");

        ListDataProvider<String> dataProvider = new ListDataProvider<>(
                Arrays.asList("a", "b", "c"));
        Grid<String> grid = new Grid<>();
        grid.setItems(dataProvider);
        grid.addColumn(x -> x).setHeader("Header");
        grid.addItemClickListener(ev -> message.setText("ItemClicked"));
        grid.setId(GRID_FILTER_FOCUSABLE_HEADER);

        Button filterButton = new Button("Filter");
        filterButton.setId("filterButton");
        Button clearFilterButton = new Button("Clear filter");
        clearFilterButton.setId("clearFilterButton");

        filterButton.addClickListener(event -> filterGrid(dataProvider, "b"));
        clearFilterButton
                .addClickListener(event -> filterGrid(dataProvider, null));

        TextField focusableHeader = new TextField();
        focusableHeader.setId("focusableHeader");
        grid.prependHeaderRow().getCells().get(0).setComponent(focusableHeader);
        add(grid, filterButton, clearFilterButton, message);
    }

    private void filterGrid(ListDataProvider<String> dataProvider,
            String filterValue) {
        dataProvider.clearFilters();
        if (filterValue != null) {
            dataProvider.addFilter(
                    item -> StringUtils.containsIgnoreCase(item, filterValue));
        }
    }
}
