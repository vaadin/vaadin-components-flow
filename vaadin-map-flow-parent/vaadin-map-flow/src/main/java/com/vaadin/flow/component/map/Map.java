package com.vaadin.flow.component.map;

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

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.map.configuration.Configuration;
import com.vaadin.flow.component.map.configuration.layer.Layer;
import com.vaadin.flow.component.map.configuration.layer.TileLayer;
import com.vaadin.flow.component.map.configuration.source.OSMSource;

@Tag("vaadin-map")
// TODO: Enable once released
// @NpmPackage(value = "@vaadin/map", version = "23.0.0-alpha4")
// TODO: Include non-themed module `@vaadin/map/src/vaadin-map.js` when theme module is ready
@JsModule("@vaadin/map/vaadin-map.js")
public class Map extends MapBase {

    private Layer backgroundLayer;

    public Map() {
        super();
        OSMSource source = new OSMSource();
        TileLayer baseLayer = new TileLayer();
        baseLayer.setSource(source);
        setBackgroundLayer(baseLayer);
    }

    public Configuration getRawConfiguration() {
        return getConfiguration();
    }

    public Layer getBackgroundLayer() {
        return backgroundLayer;
    }

    public void setBackgroundLayer(Layer backgroundLayer) {
        if (this.backgroundLayer != null) {
            getConfiguration().removeLayer(this.backgroundLayer);
        }
        this.backgroundLayer = backgroundLayer;
        getConfiguration().prependLayer(backgroundLayer);
    }

    public void addLayer(Layer layer) {
        getConfiguration().addLayer(layer);
    }

    public void removeLayer(Layer layer) {
        getConfiguration().removeLayer(layer);
    }
}
