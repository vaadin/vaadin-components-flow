package com.vaadin.flow.component.map.demo;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.map.configuration.layer.ClusterLayer;
import com.vaadin.flow.router.Route;

import java.util.Random;

@Route("vaadin-map/demo/cluster-source")
public class ClusterDemo extends Div {
    public ClusterDemo() {
        Map map = new Map();
        add(map);

        int numFeatures = 5000;
        Random random = new Random(1234);

        ClusterLayer clusterLayer = new ClusterLayer();
        clusterLayer.getSource().setDistance(50);

        for (int i = 0; i < numFeatures; i++) {
            var latitude = random.nextDouble() * 180 - 90;
            var longitude = random.nextDouble() * 360 - 180;
            MarkerFeature marker = new MarkerFeature(new Coordinate(longitude, latitude));
            marker.setText("Marker " + i);
            clusterLayer.addFeature(marker);
        }

        map.addLayer(clusterLayer);
    }
}
