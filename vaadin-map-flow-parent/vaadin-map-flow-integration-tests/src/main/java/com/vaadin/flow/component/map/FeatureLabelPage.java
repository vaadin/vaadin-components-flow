package com.vaadin.flow.component.map;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.map.configuration.style.Text;
import com.vaadin.flow.router.Route;

@Route("vaadin-map/feature-label")
public class FeatureLabelPage extends Div {
    public FeatureLabelPage() {
        Map map = new Map();
        map.getFeatureLayer().setId("feature-layer");

        MarkerFeature marker1 = new MarkerFeature();
        marker1.setId("marker1");
        marker1.setLabel("Marker label 1");
        map.getFeatureLayer().addFeature(marker1);

        MarkerFeature marker2 = new MarkerFeature(new Coordinate(30, 0));
        marker2.setId("marker2");
        marker2.setLabel("Marker label 2");
        map.getFeatureLayer().addFeature(marker2);

        MarkerFeature marker3 = new MarkerFeature(new Coordinate(60, 0));
        marker3.setId("marker3");
        marker3.setLabel("Marker label 3");
        map.getFeatureLayer().addFeature(marker3);

        NativeButton updateLabelText = new NativeButton("Update label text",
                e -> {
                    marker1.setLabel("Updated label 1");
                    marker2.setLabel("Updated label 2");
                    marker3.setLabel("Updated label 3");
                });
        updateLabelText.setId("update-label-text");

        NativeButton removeLabelText = new NativeButton("Remove label texts",
                e -> {
                    marker1.setLabel(null);
                    marker2.setLabel(null);
                    marker3.setLabel(null);
                });
        removeLabelText.setId("remove-label-text");

        NativeButton setLabelStyle = new NativeButton("Set custom label style",
                e -> {
                    marker1.getStyle().setText(createCustomLabelStyle());
                });
        setLabelStyle.setId("set-label-style");

        NativeButton updateLabelStyle = new NativeButton(
                "Update custom label style", e -> {
                    marker1.getStyle().getText().setFont("15px sans-serif");
                });
        updateLabelStyle.setId("update-label-style");

        NativeButton removeLabelStyle = new NativeButton(
                "Remove custom label style", e -> {
                    marker1.getStyle().setText(null);
                });
        removeLabelStyle.setId("remove-label-style");

        add(map);
        add(new Div(updateLabelText, removeLabelText, setLabelStyle,
                updateLabelStyle, removeLabelStyle));
    }

    private Text createCustomLabelStyle() {
        Text textStyle = new Text();
        textStyle.setFont("bold 13px monospace");
        textStyle.setOffset(30, 0);
        textStyle.setTextAlign(Text.TextAlign.LEFT);
        textStyle.setTextBaseline(Text.TextBaseline.BOTTOM);
        textStyle.setFill("#fff");
        textStyle.setStroke("#000", 5);
        textStyle.setBackgroundFill("#1F6B75");
        textStyle.setBackgroundStroke("#fff", 2);
        textStyle.setPadding(3);

        return textStyle;
    }
}
