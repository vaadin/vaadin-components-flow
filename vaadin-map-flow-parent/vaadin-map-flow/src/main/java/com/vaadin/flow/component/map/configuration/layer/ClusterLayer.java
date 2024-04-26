package com.vaadin.flow.component.map.configuration.layer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.map.configuration.Feature;
import com.vaadin.flow.component.map.configuration.source.ClusterSource;
import com.vaadin.flow.component.map.configuration.source.VectorSource;

import java.util.List;

public class ClusterLayer extends VectorLayer {
    public ClusterLayer() {
        this.setSource(new ClusterSource());
    }

    @Override
    public ClusterSource getSource() {
        return (ClusterSource) super.getSource();
    }

    @Override
    public void setSource(VectorSource source) {
        if (!(source instanceof ClusterSource)) {
            throw new IllegalArgumentException("Source must be an instance of ClusterSource");
        }
        super.setSource(source);
    }

    @JsonIgnore
    public List<Feature> getFeatures() {
        return getSource().getFeatures();
    }

    public void addFeature(Feature feature) {
        this.getSource().addFeature(feature);
    }

    public void removeFeature(Feature feature) {
        this.getSource().removeFeature(feature);
    }

    public void removeAllFeatures() {
        this.getSource().removeAllFeatures();
    }
}
