/**
 * Copyright 2000-2025 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>} for the full
 * license.
 */
package com.vaadin.flow.component.crud;

import java.util.List;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.util.SharedUtil;

/**
 * A simple grid implementation for Crud that allows searching and sorting
 * backed by a data provider.
 *
 * @param <E>
 *            the bean type
 */
public class CrudGrid<E> extends Grid<E> {

    private final Class<E> beanType;
    private final boolean autogenerated;
    private final CrudFilter filter = new CrudFilter();
    private DataProvider<E, ?> dataProvider;

    /**
     * Instantiates a new CrudGrid for the supplied bean type.
     *
     * @param beanType
     *            the bean type
     * @param enableDefaultFilters
     *            true to enable filtering or false to disable
     */
    public CrudGrid(Class<E> beanType, boolean enableDefaultFilters) {
        this(beanType, enableDefaultFilters, false);
    }

    CrudGrid(Class<E> beanType, boolean enableDefaultFilters,
            boolean autogenerated) {
        super(beanType);

        this.beanType = beanType;
        this.autogenerated = autogenerated;

        if (enableDefaultFilters) {
            setupFiltering();
        }

        setupSorting();
        Crud.addEditColumn(this);
        setSelectionMode(SelectionMode.NONE);

        ComponentUtil.addListener(this, CrudI18nUpdatedEvent.class, event -> {
            if (Crud.hasEditColumn(this)) {
                Crud.removeEditColumn(this);
                Crud.addEditColumn(this, event.getI18n());
            }
        });
    }

    private void setupFiltering() {
        final HeaderRow filterRow = this.appendHeaderRow();
        getColumns().forEach(column -> {
            final TextField field = new TextField();
            field.getElement().setAttribute("crud-role", "Search");
            field.getElement().setAttribute("aria-label", "Filter by "
                    + SharedUtil.propertyIdToHumanFriendly(column.getKey()));

            field.addValueChangeListener(event -> {
                filter.getConstraints().remove(column.getKey());

                if (!field.isEmpty()) {
                    filter.getConstraints().put(column.getKey(),
                            event.getValue());
                }

                super.getDataProvider().refreshAll();
            });

            field.setValueChangeMode(ValueChangeMode.EAGER);

            filterRow.getCell(column).setComponent(field);
            field.setSizeFull();
            field.setPlaceholder("Filter");
        });
    }

    private void setupSorting() {
        setMultiSort(true);
        addSortListener(event -> {
            filter.getSortOrders().clear();
            event.getSortOrder().forEach(e -> filter.getSortOrders()
                    .put(e.getSorted().getKey(), e.getDirection()));
            super.getDataProvider().refreshAll();
        });
    }

    /**
     * Gets the filter applied to this grid
     *
     * @return the filter
     */
    public CrudFilter getFilter() {
        return filter;
    }

    /**
     * Gets the data provider set to the grid.
     *
     * @return the data provider of this grid, not {@code null}
     */
    @Override
    public DataProvider<E, ?> getDataProvider() {
        return dataProvider;
    }

    /**
     * Sets a DataProvider&lt;E, CrudFilter&gt;
     *
     * @param dataProvider
     *            a {@link DataProvider}
     * @see CrudFilter
     * @throws IllegalArgumentException
     *             if the supplied data provider is not a DataProvider&lt;E,
     *             CrudFilter&gt;
     */
    @Override
    public void setDataProvider(DataProvider<E, ?> dataProvider)
            throws IllegalArgumentException {
        // Attempt a cast to ensure that the captured ? is actually a CrudFilter
        // Unfortunately this cannot be enforced by the compiler
        try {
            ConfigurableFilterDataProvider<E, Void, CrudFilter> provider = ((DataProvider<E, CrudFilter>) dataProvider)
                    .withConfigurableFilter();

            provider.setFilter(filter);

            super.setDataProvider(provider);

            // Keep a reference to the original data provider being wrapped
            this.dataProvider = dataProvider;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("DataProvider<"
                    + beanType.getSimpleName() + ", CrudFilter> expected", ex);
        }
    }

    void addCrudThemeVariants(List<String> variantNames) {
        if (autogenerated) {
            getThemeNames().addAll(variantNames);
        }
    }

    void removeCrudThemeVariants(List<String> variantNames) {
        if (autogenerated) {
            getThemeNames().removeAll(variantNames);
        }
    }
}
