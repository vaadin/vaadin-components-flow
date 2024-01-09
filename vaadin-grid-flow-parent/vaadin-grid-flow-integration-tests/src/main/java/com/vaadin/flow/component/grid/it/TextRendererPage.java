/*
 * Copyright 2000-2024 Vaadin Ltd.
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
package com.vaadin.flow.component.grid.it;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;

@Route("vaadin-grid/text-renderer")
public class TextRendererPage extends Div {

    public TextRendererPage() {
        Grid<String> grid = new Grid<>();
        grid.setItems("foo", "bar");

        Renderer<String> renderer = new ComponentRenderer<Text, String>(
                item -> new Text(item));
        grid.addColumn(renderer).setHeader("Header");
        add(grid);

        NativeButton button = new NativeButton("Refresh data provider",
                event -> {
                    grid.getDataProvider().refreshAll();
                    grid.getClassNames().add("refreshed");
                });
        button.setId("refresh");
        add(button);
    }
}
