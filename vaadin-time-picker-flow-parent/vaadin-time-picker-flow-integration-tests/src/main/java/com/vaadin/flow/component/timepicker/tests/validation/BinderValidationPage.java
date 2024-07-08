/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.timepicker.tests.validation;

import java.time.LocalTime;

import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.tests.validation.AbstractValidationPage;

@Route("vaadin-time-picker/validation/binder")
public class BinderValidationPage extends AbstractValidationPage<TimePicker> {
    public static final String MIN_INPUT = "min-input";
    public static final String MAX_INPUT = "max-input";
    public static final String EXPECTED_VALUE_INPUT = "expected-value-input";
    public static final String CLEAR_VALUE_BUTTON = "clear-value-button";

    public static final String REQUIRED_ERROR_MESSAGE = "The field is required";
    public static final String UNEXPECTED_VALUE_ERROR_MESSAGE = "The field doesn't match the expected value";

    public static class Bean {
        private LocalTime property;

        public LocalTime getProperty() {
            return property;
        }

        public void setProperty(LocalTime property) {
            this.property = property;
        }
    }

    protected Binder<Bean> binder;

    private LocalTime expectedValue;

    public BinderValidationPage() {
        super();

        binder = new Binder<>(Bean.class);
        binder.forField(testField).asRequired(REQUIRED_ERROR_MESSAGE)
                .withValidator(value -> value.equals(expectedValue),
                        UNEXPECTED_VALUE_ERROR_MESSAGE)
                .bind("property");

        add(createInput(EXPECTED_VALUE_INPUT, "Set expected time", event -> {
            LocalTime value = LocalTime.parse(event.getValue());
            expectedValue = value;
        }));

        add(createInput(MIN_INPUT, "Set min time", event -> {
            LocalTime value = LocalTime.parse(event.getValue());
            testField.setMinTime(value);
        }));

        add(createInput(MAX_INPUT, "Set max time", event -> {
            LocalTime value = LocalTime.parse(event.getValue());
            testField.setMaxTime(value);
        }));

        add(createButton(CLEAR_VALUE_BUTTON, "Clear value", event -> {
            testField.clear();
        }));
    }

    protected TimePicker createTestField() {
        return new TimePicker();
    }
}
