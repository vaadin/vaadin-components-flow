/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See  {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.grid;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.vaadin.flow.data.provider.DataGenerator;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.Rendering;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.internal.JsonSerializer;

import elemental.json.Json;

/**
 * Renderer for columns that doesn't use any template for rendering its contents
 * (only the value from the object model). In such cases, a {@code template}
 * element is not needed on the client-side, only the {@code path} property.
 *
 * @author Vaadin Ltd.
 *
 * @param <SOURCE>
 *            the object model type
 * @see Grid#addColumn(ValueProvider)
 */
public class ColumnPathRenderer<SOURCE> extends Renderer<SOURCE> {

    /**
     * Creates a new renderer based on the property and the value provider for
     * that property.
     *
     * @param property
     *            the property name
     * @param provider
     *            the value provider for the property
     */
    public ColumnPathRenderer(String property,
            ValueProvider<SOURCE, ?> provider) {
        setProperty(property, provider);
    }

    @Override
    public Rendering<SOURCE> render(Element container,
            DataKeyMapper<SOURCE> keyMapper) {
        return render(container, keyMapper, null);
    }

    @Override
    public Rendering<SOURCE> render(Element container,
            DataKeyMapper<SOURCE> keyMapper, Element contentTemplate) {

        Map<String, ValueProvider<SOURCE, ?>> valueProviders = getValueProviders();
        if (valueProviders.size() != 1) {
            throw new IllegalStateException(
                    "There should be only one ValueProvider for the ColumnPathRenderer");
        }
        String property = valueProviders.keySet().iterator().next();
        container.setProperty("path", property);

        // disables the automatic creation of headers when the path is used
        container.setPropertyJson("header", Json.createNull());

        return new SingleValueProviderRendering();
    }

    private class SingleValueProviderRendering implements Rendering<SOURCE> {

        @Override
        public Optional<DataGenerator<SOURCE>> getDataGenerator() {
            Map<String, ValueProvider<SOURCE, ?>> valueProviders = getValueProviders();
            if (valueProviders.size() != 1) {
                throw new IllegalStateException(
                        "There should be only one ValueProvider for the ColumnPathRenderer");
            }
            Entry<String, ValueProvider<SOURCE, ?>> entry = valueProviders
                    .entrySet().iterator().next();
            String property = entry.getKey();
            ValueProvider<SOURCE, ?> provider = entry.getValue();

            return Optional.of((item, jsonObject) -> jsonObject.put(property,
                    JsonSerializer.toJson(provider.apply(item))));
        }

        @Override
        public Element getTemplateElement() {
            return null;
        }
    }

}
