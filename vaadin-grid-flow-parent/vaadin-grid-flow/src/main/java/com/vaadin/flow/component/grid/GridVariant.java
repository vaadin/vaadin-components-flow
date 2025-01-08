/*
 * Copyright 2000-2025 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.grid;

/**
 * Set of theme variants applicable for {@code vaadin-grid} component.
 */
public enum GridVariant {
    //@formatter:off
    LUMO_NO_BORDER("no-border"),
    LUMO_NO_ROW_BORDERS("no-row-borders"),
    LUMO_COLUMN_BORDERS("column-borders"),
    LUMO_ROW_STRIPES("row-stripes"),
    LUMO_COMPACT("compact"),
    LUMO_WRAP_CELL_CONTENT("wrap-cell-content"),
    MATERIAL_COLUMN_DIVIDERS( "column-dividers");

    //@formatter:on

    private final String variant;

    GridVariant(String variant) {
        this.variant = variant;
    }

    /**
     * Gets the variant name.
     *
     * @return variant name
     */
    public String getVariantName() {
        return variant;
    }
}
