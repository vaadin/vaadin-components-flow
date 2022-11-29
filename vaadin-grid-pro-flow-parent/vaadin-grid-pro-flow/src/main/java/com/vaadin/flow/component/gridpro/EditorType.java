package com.vaadin.flow.component.gridpro;

/**
 * Copyright (C) 2022 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */

/**
 * Set of editor types applicable for editor of the
 * {@code vaadin-grid-edit-column} component.
 */
public enum EditorType {
    TEXT("text"), CHECKBOX("checkbox"), SELECT("select"), CUSTOM("custom");

    private final String type;

    EditorType(String type) {
        this.type = type;
    }

    /**
     * Gets the type name.
     *
     * @return type name
     */
    public String getTypeName() {
        return type;
    }
}
