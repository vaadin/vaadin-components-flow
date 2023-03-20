/**
 * Copyright (C) 2000-2023 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.datetimepicker;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.testutil.ValidationTestView;

import java.time.LocalDateTime;

/**
 * View for testing validation with {@link DateTimePicker}.
 */
@Route("vaadin-date-time-picker/date-time-picker-validation")
public class DateTimePickerValidationPage extends ValidationTestView {

    public DateTimePickerValidationPage() {
        createPickerWithMaxAndMinValues();
    }

    @Override
    protected HasValidation getValidationComponent() {
        return new DateTimePicker();
    }

    private void createPickerWithMaxAndMinValues() {
        final DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setMin(LocalDateTime.of(2020, 6, 7, 1, 0));
        dateTimePicker.setMax(LocalDateTime.of(2020, 6, 7, 3, 0));
        dateTimePicker.setId("picker-with-valid-range");

        final Div isValid = new Div();
        isValid.setId("is-invalid");
        final NativeButton checkIsValid = new NativeButton(
                "Check if current value of the field is invalid");
        checkIsValid.setId("check-is-invalid");
        checkIsValid.addClickListener(event -> isValid
                .setText(dateTimePicker.isInvalid() ? "invalid" : "valid"));
        add(dateTimePicker, checkIsValid, isValid);
    }
}
