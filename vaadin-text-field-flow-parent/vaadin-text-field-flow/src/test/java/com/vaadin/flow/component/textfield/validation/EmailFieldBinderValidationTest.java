/*
 * Copyright 2000-2023 Vaadin Ltd.
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
package com.vaadin.flow.component.textfield.validation;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.Objects;

public class EmailFieldBinderValidationTest
        extends AbstractBinderValidationTest<String, EmailField> {

    @Override
    protected void initField() {
        field = new EmailField();
        // To disable pattern validation
        field.setPattern(null);
        field.setMaxLength(20);
    }

    @Override
    protected void setValidValue() {
        field.setValue("contact@example.com");
    }

    @Override
    protected void setComponentInvalidValue() {
        field.setValue("reallylongemail@example.com");
    }

    @Override
    protected void setBinderInvalidValue() {
        field.setValue("contact@another.com");
    }

    @Override
    protected SerializablePredicate<? super String> getValidator() {
        return value -> Objects.equals(value, "")
                || value.contains("@example.com");
    }
}
