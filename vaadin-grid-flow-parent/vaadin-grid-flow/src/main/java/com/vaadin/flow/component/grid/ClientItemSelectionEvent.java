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
package com.vaadin.flow.component.grid;

import com.vaadin.flow.component.ComponentEvent;

public class ClientItemSelectionEvent<T> extends ComponentEvent<Grid<T>> {
    private final T item;
    private final boolean isSelected;
    private final boolean isShiftKey;

    public ClientItemSelectionEvent(Grid<T> source, T item, boolean isSelected,
            boolean isShiftKey) {
        super(source, true);
        this.item = item;
        this.isSelected = isSelected;
        this.isShiftKey = isShiftKey;
    }

    public T getItem() {
        return item;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isShiftKey() {
        return isShiftKey;
    }
}
