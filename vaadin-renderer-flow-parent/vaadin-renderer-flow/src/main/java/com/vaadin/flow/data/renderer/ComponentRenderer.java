/*
 * Copyright 2000-2022 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.data.renderer;

import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.CompositeDataGenerator;
import com.vaadin.flow.data.provider.DataGenerator;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.shared.Registration;

/**
 * Base class for all renderers that support arbitrary {@link Component}s.
 * <p>
 * Components that support renderers should use the appropriate method from this
 * class to provide component rendering: {@link #render(Element, DataKeyMapper)}
 * for components that uses {@code <template>}, and
 * {@link #createComponent(Object)} for components that use light-dom.
 *
 * @author Vaadin Ltd
 *
 * @param <COMPONENT>
 *            the type of the output component
 * @param <SOURCE>
 *            the type of the input model object
 */
public class ComponentRenderer<COMPONENT extends Component, SOURCE>
        extends LitRenderer<SOURCE> {

    private Element container;
    private SerializableSupplier<COMPONENT> componentSupplier;
    private SerializableFunction<SOURCE, COMPONENT> componentFunction;
    private SerializableBiFunction<Component, SOURCE, Component> componentUpdateFunction;
    private SerializableBiConsumer<COMPONENT, SOURCE> itemConsumer;

    public ComponentRenderer(
            SerializableFunction<SOURCE, COMPONENT> componentFunction,
            SerializableBiFunction<Component, SOURCE, Component> componentUpdateFunction) {
        super("");
        this.componentFunction = componentFunction;
        this.componentUpdateFunction = componentUpdateFunction;
    }

    public ComponentRenderer(
            SerializableFunction<SOURCE, COMPONENT> componentFunction) {
        this(componentFunction, null);
    }

    public ComponentRenderer(SerializableSupplier<COMPONENT> componentSupplier,
            SerializableBiConsumer<COMPONENT, SOURCE> itemConsumer) {
        super("");
        this.componentSupplier = componentSupplier;
        this.itemConsumer = itemConsumer;
    }

    @Override
    protected String getTemplateExpression() {
        return "<flow-component-renderer nodeid=${item.nodeid} appid='"
                + UI.getCurrent().getInternals().getAppId()
                + "'></flow-component-renderer>";
    }

    public ComponentRenderer(
            SerializableSupplier<COMPONENT> componentSupplier) {
        this(componentSupplier, null);
    }

    protected ComponentRenderer() {
        super("");
    }

    protected Element getContainer() {
        return container;
    }

    @Override
    public Rendering<SOURCE> render(Element container,
            DataKeyMapper<SOURCE> keyMapper, String rendererName) {
        this.container = container;
        var rendering = super.render(container, keyMapper, rendererName);

        return getRendering(keyMapper, rendering.getDataGenerator(),
                rendering.getRegistration());
    }

    Rendering<SOURCE> getRendering(DataKeyMapper<SOURCE> keyMapper,
            Optional<DataGenerator<SOURCE>> dataGenerator,
            Registration registration) {
        return new Rendering<SOURCE>() {
            @Override
            public Optional<DataGenerator<SOURCE>> getDataGenerator() {
                var generator = new CompositeDataGenerator<SOURCE>();

                var componentDataGenerator = new ComponentDataGenerator<SOURCE>(
                        ComponentRenderer.this,
                        keyMapper == null ? null : keyMapper::key);
                componentDataGenerator.setContainer(container);
                componentDataGenerator.setNodeIdPropertyName(
                        getPropertyNamespace() + "nodeid");
                generator.addDataGenerator(componentDataGenerator);

                generator.addDataGenerator(dataGenerator.get());
                return Optional.of(generator);
            }

            @Override
            public Element getTemplateElement() {
                return null;
            }

            @Override
            public Registration getRegistration() {
                return registration;
            }
        };
    }

    /**
     * Called when the item is updated. By default, a new {@link Component} is
     * created (via {@link #createComponent(Object)}) when the item is updated,
     * but setting a update function via the
     * {@link #ComponentRenderer(SerializableFunction, SerializableBiFunction)}
     * can change the behavior.
     *
     * @param currentComponent
     *            the current component used to render the item, not
     *            <code>null</code>
     * @param item
     *            the updated item
     * @return the component that should be used to render the updated item. The
     *         same instance can be returned, or a totally new one, but not
     *         <code>null</code>.
     */
    public Component updateComponent(Component currentComponent, SOURCE item) {
        if (componentUpdateFunction != null) {
            return componentUpdateFunction.apply(currentComponent, item);
        }
        return createComponent(item);
    }

    /**
     * Creates a component for a given object model item. Subclasses can
     * override this method to provide specific behavior.
     *
     * @param item
     *            the model item, possibly <code>null</code>
     * @return a component instance representing the provided item
     */
    public COMPONENT createComponent(SOURCE item) {
        if (componentFunction != null) {
            return componentFunction.apply(item);
        }
        COMPONENT component = componentSupplier.get();
        if (itemConsumer != null) {
            itemConsumer.accept(component, item);
        }
        return component;
    }

}
