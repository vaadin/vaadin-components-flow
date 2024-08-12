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
package com.vaadin.flow.component.timepicker.tests.validation;

import java.time.LocalTime;

import org.junit.Test;

import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.tests.validation.AbstractBasicValidationTest;

public class BasicValidationTest
        extends AbstractBasicValidationTest<TimePicker, LocalTime> {
    @Test
    public void addValidationStatusChangeListener_addAnotherListenerOnInvocation_noExceptions() {
        testField.addValidationStatusChangeListener(event1 -> {
            testField.addValidationStatusChangeListener(event2 -> {
            });
        });

        // Trigger ValidationStatusChangeEvent
        testField.getElement().setProperty("_hasInputValue", true);
        testField.clear();
    }

    protected TimePicker createTestField() {
        return new TimePicker();
    }
}
