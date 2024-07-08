/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.textfield.tests.validation;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.tests.validation.AbstractValidationPage;

@Route("vaadin-integer-field/validation/binder")
public class IntegerFieldValidationBinderPage
        extends AbstractValidationPage<IntegerField> {
    public static final String STEP_INPUT = "step-input";
    public static final String MIN_INPUT = "min-input";
    public static final String MAX_INPUT = "max-input";
    public static final String EXPECTED_VALUE_INPUT = "expected-value-input";
    public static final String CLEAR_VALUE_BUTTON = "clear-value-button";

    public static final String REQUIRED_ERROR_MESSAGE = "The field is required";
    public static final String UNEXPECTED_VALUE_ERROR_MESSAGE = "The field doesn't match the expected value";

    public static class Bean {
        private Integer property;

        public Integer getProperty() {
            return property;
        }

        public void setProperty(Integer property) {
            this.property = property;
        }
    }

    protected Binder<Bean> binder;

    private Integer expectedValue;

    public IntegerFieldValidationBinderPage() {
        super();

        binder = new Binder<>(Bean.class);
        binder.forField(testField).asRequired(REQUIRED_ERROR_MESSAGE)
                .withValidator(value -> value.equals(expectedValue),
                        UNEXPECTED_VALUE_ERROR_MESSAGE)
                .bind("property");

        add(createInput(EXPECTED_VALUE_INPUT, "Set expected value", event -> {
            expectedValue = Integer.parseInt(event.getValue());
        }));

        add(createInput(STEP_INPUT, "Set step", event -> {
            int value = Integer.parseInt(event.getValue());
            testField.setStep(value);
        }));

        add(createInput(MIN_INPUT, "Set min", event -> {
            int value = Integer.parseInt(event.getValue());
            testField.setMin(value);
        }));

        add(createInput(MAX_INPUT, "Set max", event -> {
            int value = Integer.parseInt(event.getValue());
            testField.setMax(value);
        }));

        add(createButton(CLEAR_VALUE_BUTTON, "Clear value", event -> {
            testField.clear();
        }));
    }

    protected IntegerField createTestField() {
        return new IntegerField();
    }
}
