package com.vaadin.flow.component.map.configuration.source;

/*
 * #%L
 * Vaadin Map
 * %%
 * Copyright (C) 2022 - 2022 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Developer License
 * 4.0 (CVDLv4).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * For the full License, see <https://vaadin.com/license/cvdl-4.0>.
 * #L%
 */

/**
 * Abstract base class for all sources providing a single image
 */
public abstract class ImageSource extends Source {
    public ImageSource(Options options) {
        super(options);
    }

    public static class Options extends Source.Options {
    }
}
