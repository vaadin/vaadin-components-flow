/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.datetimepicker;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.Route;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Route("vaadin-date-time-picker/date-time-picker-value-change")
public class DateTimePickerValueChangePage extends Div {

    Div changeLog;

    public DateTimePickerValueChangePage() {
        createDatePicker();
        createChangeLog();
    }

    private void createDatePicker() {
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setId("date-time-picker");
        dateTimePicker.setLabel("Date time picker");
        dateTimePicker.addValueChangeListener(this::logValueChangedEvent);

        NativeButton setCurrentTimeButton = new NativeButton(
                "Set current date and time", event -> {
                    dateTimePicker.setValue(LocalDateTime.now());
                });
        setCurrentTimeButton.setId("set-current-date-time");

        NativeButton setSecondsPrecisionButton = new NativeButton(
                "Set seconds precision", event -> {
                    dateTimePicker.setStep(Duration.of(30, ChronoUnit.SECONDS));
                });
        setSecondsPrecisionButton.setId("set-seconds-precision");

        NativeButton setMillisPrecisionButton = new NativeButton(
                "Set millisecond precision", event -> {
                    dateTimePicker.setStep(Duration.of(500, ChronoUnit.MILLIS));
                });
        setMillisPrecisionButton.setId("set-millis-precision");

        NativeButton setReadonlyButton = new NativeButton("Set readonly",
                event -> {
                    dateTimePicker.setReadOnly(true);
                });
        setReadonlyButton.setId("set-readonly");

        add(dateTimePicker);
        add(new Div(setCurrentTimeButton, setSecondsPrecisionButton,
                setMillisPrecisionButton, setReadonlyButton));
    }

    private void createChangeLog() {
        changeLog = new Div();
        changeLog.setId("change-log");
        changeLog.getStyle().set("whiteSpace", "pre");
        add(changeLog);
    }

    private void logValueChangedEvent(
            AbstractField.ComponentValueChangeEvent<DateTimePicker, LocalDateTime> event) {
        String source = event.isFromClient() ? "client" : "server";
        String value = event.getValue().format(DateTimeFormatter.ISO_DATE_TIME);

        String logEntry = String.format("source: %s; value: %s%n", source,
                value);

        changeLog.setText(changeLog.getText() + logEntry);
    }
}
