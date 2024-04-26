package com.vaadin.flow.component.map.configuration.source;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vaadin.flow.component.map.configuration.Constants;
import com.vaadin.flow.component.map.configuration.Feature;

import java.util.List;

public class ClusterSource extends VectorSource {
    private final VectorSource source = new VectorSource();
    private int distance = 20;
    private int minDistance = 0;


    public ClusterSource() {
        this(new Options());
    }

    public ClusterSource(Options options) {
        super(options);
        addChild(source);
    }

    @Override
    public String getType() {
        return Constants.OL_SOURCE_CLUSTER;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public VectorSource getSource() {
        return source;
    }

    @Override
    public List<Feature> getFeatures() {
        return source.getFeatures();
    }

    @Override
    public void addFeature(Feature feature) {
        source.addFeature(feature);
    }

    @Override
    public void removeFeature(Feature feature) {
        source.removeFeature(feature);
    }

    @Override
    public void removeAllFeatures() {
        source.removeAllFeatures();
    }
}

