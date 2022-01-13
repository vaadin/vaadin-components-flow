package com.vaadin.flow.component.map.configuration;

import com.vaadin.flow.component.map.configuration.layer.Layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Configuration extends AbstractConfigurationObject {
    private final List<Layer> layers = new ArrayList<>();

    @Override
    public String getType() {
        return Constants.OL_MAP;
    }

    public List<Layer> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    public void addLayer(Layer layer) {
        Objects.requireNonNull(layer);

        layer.addPropertyChangeListener(this::notifyChange);

        layers.add(layer);
        notifyChange();
    }

    public void removeLayer(Layer layer) {
        Objects.requireNonNull(layer);

        layer.removePropertyChangeListener(this::notifyChange);

        layers.remove(layer);
        notifyChange();
    }
}
