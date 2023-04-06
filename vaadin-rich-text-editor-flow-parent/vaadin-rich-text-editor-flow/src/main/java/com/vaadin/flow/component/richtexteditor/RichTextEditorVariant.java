/**
 * Copyright (C) 2000-2023 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.richtexteditor;

/**
 * Set of theme variants applicable for {@code vaadin-rich-text-editor}
 * component.
 */
public enum RichTextEditorVariant {
    LUMO_NO_BORDER("no-border"), LUMO_COMPACT("compact"), MATERIAL_NO_BORDER(
            "no-border");

    private final String variant;

    RichTextEditorVariant(String variant) {
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
