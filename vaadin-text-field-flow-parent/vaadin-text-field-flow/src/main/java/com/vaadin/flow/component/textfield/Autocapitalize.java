/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See  {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.textfield;

/**
 * Capitalization options for the {@code autocapitalize} attribute.
 */
public enum Autocapitalize {

    /**
     * Completely disable automatic capitalization.
     */
    NONE("none"),

    /**
     * Automatically capitalize the first letter of sentences.
     */
    SENTENCES("sentences"),

    /**
     * Automatically capitalize the first letter of words.
     */
    WORDS("words"),

    /**
     * Automatically capitalize all characters.
     */
    CHARACTERS("characters");

    final String value;

    Autocapitalize(String value) {
        this.value = value;
    }
}
