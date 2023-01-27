package com.vaadin.flow.component.datepicker;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.Route;

@Route("vaadin-date-picker/date-picker-locale")
public class DatePickerLocalePage extends Div {

    private final LocalDate may3rd = LocalDate.of(2018, Month.MAY, 3);
    private final LocalDate april23rd = LocalDate.of(2018, Month.APRIL, 23);

    public DatePickerLocalePage() {
        DatePicker datePicker = new DatePicker(april23rd, Locale.CHINA);
        datePicker.setId("locale-picker-server-with-value");

        NativeButton locale = new NativeButton("Locale: UK");
        locale.setId("uk-locale");

        locale.addClickListener(e -> datePicker.setLocale(Locale.UK));

        DatePicker frenchLocale = new DatePicker();
        frenchLocale.setId("french-locale-date-picker");

        frenchLocale.setLocale(Locale.FRANCE);
        frenchLocale.setValue(may3rd);

        DatePicker german = new DatePicker();
        german.setLocale(Locale.GERMAN);
        german.setId("german-locale-date-picker");

        add(datePicker, locale, frenchLocale, german);

        DatePicker polandDatePicker = new DatePicker(may3rd,
                new Locale("pl", "PL"));
        polandDatePicker.setId("polish-locale-date-picker");
        add(polandDatePicker);

        DatePicker korean = new DatePicker(may3rd, new Locale("ko", "KR"));
        korean.setId("korean-locale-date-picker");
        add(korean);

        DatePicker datePickerRef = new DatePicker();
        datePickerRef.setId("picker");
        Input localeInput = new Input();
        localeInput.setId("locale-input");
        localeInput.setPlaceholder("Enter locale string");

        NativeButton applyLocale = new NativeButton("Apply locale", e -> {
            String localeString = localeInput.getValue();
            String[] localeParts = localeString.split("_");
            Locale localeRef = null;
            if (localeParts.length == 1) {
                localeRef = new Locale(localeParts[0]);
            }
            if (localeParts.length == 2) {
                localeRef = new Locale(localeParts[0], localeParts[1]);
            }
            if (localeParts.length == 3) {
                localeRef = new Locale(localeParts[0], localeParts[1],
                        localeParts[2]);
            }
            datePickerRef.setLocale(localeRef);
        });
        applyLocale.setId("apply-locale");

        add(datePickerRef, localeInput, applyLocale);
        NativeButton applyCustomReferenceDate = new NativeButton(
                "Apply custom reference date", e -> {
                    DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
                    i18n.setReferenceDate(LocalDate.of(1980, 2, 2));
                    datePickerRef.setI18n(i18n);
                });
        applyCustomReferenceDate.setId("apply-custom-reference-date");

        add(datePickerRef, localeInput, applyLocale, applyCustomReferenceDate);
    }
}
