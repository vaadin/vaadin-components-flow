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
package com.vaadin.flow.component.mixin;

import com.vaadin.flow.component.HasElement;

/**
 * Mixin interface for components that support a clear button.
 * <p>
 * Used to toggle the visibility of the clear button.
 *
 * @author Vaadin Ltd
 */
public interface HasClearButton extends HasElement {

    /**
     * Gets the visibility state of the button which clears the field.
     *
     * @return <code>true</code> if the button is visible, <code>false</code>
     *         otherwise
     */
    default boolean isClearButtonVisible() {
        return getElement().getProperty("clearButtonVisible", false);
    }

    /**
     * Set to <code>false</code> to hide the clear button which clears the
     * field.
     *
     * @param clearButtonVisible
     *            <code>true</code> to set the button visible,
     *            <code>false</code> otherwise
     */
    default void setClearButtonVisible(boolean clearButtonVisible) {
        getElement().setProperty("clearButtonVisible", clearButtonVisible);
    }
}
