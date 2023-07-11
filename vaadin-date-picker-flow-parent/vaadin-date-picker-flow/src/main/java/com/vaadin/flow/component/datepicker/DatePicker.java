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
package com.vaadin.flow.component.datepicker;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.shared.ClientValidationUtil;
import com.vaadin.flow.component.shared.HasClientValidation;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.internal.JsonSerializer;
import com.vaadin.flow.internal.StateTree;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonObject;
import elemental.json.JsonType;

/**
 * Server-side component that encapsulates the functionality of the
 * {@code vaadin-date-picker} webcomponent.
 * <p>
 * It allows setting and getting {@link LocalDate} objects, setting minimum and
 * maximum date ranges and has internationalization support by using the
 * {@link DatePickerI18n} object.
 *
 */
@JsModule("./date-picker-datefns.js")
@JsModule("./datepickerConnector.js")
@JavaScript("frontend://date-picker-datefns.js")
@JavaScript("frontend://datepickerConnector.js")
public class DatePicker extends GeneratedVaadinDatePicker<DatePicker, LocalDate>
        implements HasSize, HasValidation, HasHelper, HasLabel,
        HasValidator<LocalDate>, HasClientValidation {

    private static final String PROP_AUTO_OPEN_DISABLED = "autoOpenDisabled";

    private DatePickerI18n i18n;

    private final static SerializableFunction<String, LocalDate> PARSER = s -> {
        return s == null || s.isEmpty() ? null : LocalDate.parse(s);
    };

    private final static SerializableFunction<LocalDate, String> FORMATTER = d -> {
        return d == null ? "" : d.toString();
    };

    private Locale locale;

    private LocalDate max;
    private LocalDate min;
    private boolean required;

    private StateTree.ExecutionRegistration pendingI18nUpdate;

    /**
     * Default constructor.
     */
    public DatePicker() {
        this((LocalDate) null);
    }

    /**
     * Convenience constructor to create a date picker with a pre-selected date
     * in current UI locale format.
     *
     * @param initialDate
     *            the pre-selected date in the picker
     * @see #setValue(Object)
     */
    public DatePicker(LocalDate initialDate) {
        super(initialDate, null, String.class, PARSER, FORMATTER);

        // workaround for https://github.com/vaadin/flow/issues/3496
        setInvalid(false);

        addValueChangeListener(e -> validate());

        if (isEnforcedFieldValidationEnabled()) {
            addClientValidatedEventListener(e -> validate());
        }
    }

    /**
     * Convenience constructor to create a date picker with a label.
     *
     * @param label
     *            the label describing the date picker
     * @see #setLabel(String)
     */
    public DatePicker(String label) {
        this();
        setLabel(label);
    }

    /**
     * Convenience constructor to create a date picker with a pre-selected date
     * in current UI locale format and a label.
     *
     * @param label
     *            the label describing the date picker
     * @param initialDate
     *            the pre-selected date in the picker
     * @see #setValue(Object)
     * @see #setLabel(String)
     */
    public DatePicker(String label, LocalDate initialDate) {
        this(initialDate);
        setLabel(label);
    }

    /**
     * Convenience constructor to create a date picker with a
     * {@link ValueChangeListener}.
     *
     * @param listener
     *            the listener to receive value change events
     * @see #addValueChangeListener(HasValue.ValueChangeListener)
     */
    public DatePicker(
            ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        this();
        addValueChangeListener(listener);
    }

    /**
     * Convenience constructor to create a date picker with a
     * {@link ValueChangeListener} and a label.
     *
     *
     * @param label
     *            the label describing the date picker
     * @param listener
     *            the listener to receive value change events
     * @see #setLabel(String)
     * @see #addValueChangeListener(HasValue.ValueChangeListener)
     */
    public DatePicker(String label,
            ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        this(label);
        addValueChangeListener(listener);
    }

    /**
     * Convenience constructor to create a date picker with a pre-selected date
     * in current UI locale format and a {@link ValueChangeListener}.
     *
     * @param initialDate
     *            the pre-selected date in the picker
     * @param listener
     *            the listener to receive value change events
     * @see #setValue(Object)
     * @see #addValueChangeListener(HasValue.ValueChangeListener)
     */
    public DatePicker(LocalDate initialDate,
            ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        this(initialDate);
        addValueChangeListener(listener);
    }

    /**
     * Convenience constructor to create a date picker with a pre-selected date
     * in current UI locale format, a {@link ValueChangeListener} and a label.
     *
     * @param label
     *            the label describing the date picker
     * @param initialDate
     *            the pre-selected date in the picker
     * @param listener
     *            the listener to receive value change events
     * @see #setLabel(String)
     * @see #setValue(Object)
     * @see #addValueChangeListener(HasValue.ValueChangeListener)
     */
    public DatePicker(String label, LocalDate initialDate,
            ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        this(initialDate);
        setLabel(label);
        addValueChangeListener(listener);
    }

    /**
     * Convenience Constructor to create a date picker with pre-selected date
     * and locale setup.
     *
     * @param initialDate
     *            the pre-selected date in the picker
     * @param locale
     *            the locale for the date picker
     */
    public DatePicker(LocalDate initialDate, Locale locale) {
        this(initialDate);
        setLocale(locale);
    }

    /**
     * Sets the minimum date in the date picker. Dates before that will be
     * disabled in the popup.
     *
     * @param min
     *            the minimum date that is allowed to be selected, or
     *            <code>null</code> to remove any minimum constraints
     */
    public void setMin(LocalDate min) {
        setMinAsString(FORMATTER.apply(min));
        this.min = min;
    }

    /**
     * Gets the minimum date in the date picker. Dates before that will be
     * disabled in the popup.
     *
     * @return the minimum date that is allowed to be selected, or
     *         <code>null</code> if there's no minimum
     */
    public LocalDate getMin() {
        return PARSER.apply(getMinAsStringString());
    }

    /**
     * Sets the maximum date in the date picker. Dates after that will be
     * disabled in the popup.
     *
     * @param max
     *            the maximum date that is allowed to be selected, or
     *            <code>null</code> to remove any maximum constraints
     */
    public void setMax(LocalDate max) {
        setMaxAsString(FORMATTER.apply(max));
        this.max = max;
    }

    /**
     * Gets the maximum date in the date picker. Dates after that will be
     * disabled in the popup.
     *
     * @return the maximum date that is allowed to be selected, or
     *         <code>null</code> if there's no maximum
     */
    public LocalDate getMax() {
        return PARSER.apply(getMaxAsStringString());
    }

    /**
     * Set the Locale for the Date Picker. The displayed date will be matched to
     * the format used in that locale.
     * <p>
     * NOTE:Supported formats are MM/DD/YYYY, DD/MM/YYYY and YYYY/MM/DD. Browser
     * compatibility can be different based on the browser and mobile devices,
     * you can check here for more details: <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleDateString">https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleDateString</a>
     * <p>
     * When using custom date formats through
     * {@link DatePicker#setI18n(DatePickerI18n)}, setting a locale has no
     * effect, and dates will always be parsed and displayed using the custom
     * date format.
     *
     * @param locale
     *            the locale set to the date picker, cannot be null
     */
    public void setLocale(Locale locale) {
        Objects.requireNonNull(locale, "Locale must not be null.");
        this.locale = locale;
        requestI18nUpdate();
    }

    /**
     * Gets the Locale for this date picker
     *
     * @return the locale used for this picker
     */
    @Override
    public Locale getLocale() {
        if (locale != null) {
            return locale;
        } else {
            return super.getLocale();
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initConnector();
        requestI18nUpdate();
        if (isEnforcedFieldValidationEnabled()) {
            ClientValidationUtil.preventWebComponentFromModifyingInvalidState(this);
        } else {
            FieldValidationUtil.disableClientValidation(this);
        }
    }

    private void initConnector() {
        runBeforeClientResponse(ui -> ui.getPage().executeJavaScript(
                "window.Vaadin.Flow.datepickerConnector.initLazy($0)",
                getElement()));
    }

    /**
     * Gets the internationalization object previously set for this component.
     * <p>
     * Note: updating the object content that is gotten from this method will
     * not update the lang on the component if not set back using
     * {@link DatePicker#setI18n(DatePickerI18n)}
     *
     * @return the i18n object. It will be <code>null</code>, If the i18n
     *         properties weren't set.
     */
    public DatePickerI18n getI18n() {
        return i18n;
    }

    /**
     * Sets the internationalization properties for this component.
     *
     * @param i18n
     *            the internationalized properties, not <code>null</code>
     */
    public void setI18n(DatePickerI18n i18n) {
        Objects.requireNonNull(i18n,
                "The I18N properties object should not be null");
        this.i18n = i18n;
        requestI18nUpdate();
    }

    private void requestI18nUpdate() {
        getUI().ifPresent(ui -> {
            if (pendingI18nUpdate != null) {
                pendingI18nUpdate.remove();
            }
            pendingI18nUpdate = ui.beforeClientResponse(this, context -> {
                pendingI18nUpdate = null;
                executeI18nUpdate();
            });
        });
    }

    /**
     * Update I18N settings in the web component. Merges the DatePickerI18N
     * settings with the current settings of the web component, and configures
     * formatting and parsing functions based on either the locale, or the
     * custom date formats specified in DatePickerI18N.
     */
    private void executeI18nUpdate() {
        JsonObject i18nObject = getI18nAsJsonObject();

        // For ill-formed locales, Locale.toLanguageTag() will append subtag
        // "lvariant" to it, which will cause the client side
        // Date().toLocaleDateString()
        // fallback to the system default locale silently.
        // This has been caught by DatePickerValidationPage::invalidLocale test
        // when running on
        // Chrome(73+)/FireFox(66)/Edge(42.17134).
        Locale appliedLocale = getLocale();
        String languageTag;
        if (!appliedLocale.toLanguageTag().contains("lvariant")) {
            languageTag = appliedLocale.toLanguageTag();
        } else if (appliedLocale.getCountry().isEmpty()) {
            languageTag = appliedLocale.getLanguage();
        } else {
            languageTag = appliedLocale.getLanguage() + "-"
                    + appliedLocale.getCountry();
        }

        // Call update function in connector with locale and I18N settings
        // The connector is expected to handle that either of those can be null
        getElement().callJsFunction("$connector.updateI18n", languageTag,
                i18nObject);
    }

    private JsonObject getI18nAsJsonObject() {
        if (i18n == null) {
            return null;
        }
        JsonObject i18nObject = (JsonObject) JsonSerializer.toJson(i18n);
        // LocalDate objects have to be explicitly added to the serialized i18n
        // object in order to be formatted correctly
        if (i18n.getReferenceDate() != null) {
            i18nObject.put("referenceDate",
                    i18n.getReferenceDate().format(DateTimeFormatter.ISO_DATE));
        }
        // Remove properties with null values to prevent errors in web component
        removeNullValuesFromJsonObject(i18nObject);
        return i18nObject;
    }

    private void removeNullValuesFromJsonObject(JsonObject jsonObject) {
        for (String key : jsonObject.keys()) {
            if (jsonObject.get(key).getType() == JsonType.NULL) {
                jsonObject.remove(key);
            }
        }
    }

    void runBeforeClientResponse(SerializableConsumer<UI> command) {
        getElement().getNode().runWhenAttached(ui -> ui
                .beforeClientResponse(this, context -> command.accept(ui)));
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            super.setErrorMessage("");
        } else {
            super.setErrorMessage(errorMessage);
        }
    }

    /**
     * Gets the current error message from the datepicker.
     *
     * @return the current error message
     */
    @Override
    public String getErrorMessage() {
        return getErrorMessageString();
    }

    @Override
    public Validator<LocalDate> getDefaultValidator() {
        if (isEnforcedFieldValidationEnabled()) {
            return (value, context) -> checkValidity(value);
        }

        return Validator.alwaysPass();
    }

    @Override
    public Registration addValidationStatusChangeListener(
            ValidationStatusChangeListener<LocalDate> listener) {
        if (isEnforcedFieldValidationEnabled()) {
            return addClientValidatedEventListener(
                    event -> listener.validationStatusChanged(
                            new ValidationStatusChangeEvent<LocalDate>(this,
                                    !isInvalid())));
        }

        return null;
    }

    @Override
    public void setInvalid(boolean invalid) {
        super.setInvalid(invalid);
    }

    /**
     * Gets the validity of the datepicker output.
     * <p>
     * return true, if the value is invalid.
     *
     * @return the {@code validity} property from the datepicker
     */
    @Override
    public boolean isInvalid() {
        return isInvalidBoolean();
    }

    private ValidationResult checkValidity(LocalDate value) {
        if (isEnforcedFieldValidationEnabled()) {
            boolean hasNonParsableValue = value == getEmptyValue()
                    && isInputValuePresent();
            if (hasNonParsableValue) {
                return ValidationResult.error("");
            }
        }

        ValidationResult greaterThanMax = checkGreaterThanMax(value, max);
        if (greaterThanMax.isError()) {
            return greaterThanMax;
        }

        ValidationResult smallerThanMin = checkSmallerThanMin(value, min);
        if (smallerThanMin.isError()) {
            return smallerThanMin;
        }

        return ValidationResult.ok();
    }

    private static <V extends Comparable<V>> ValidationResult checkGreaterThanMax(
            V value, V maxValue) {
        final boolean isGreaterThanMax = value != null && maxValue != null
                && value.compareTo(maxValue) > 0;
        if (isGreaterThanMax) {
            return ValidationResult.error("");
        }
        return ValidationResult.ok();
    }

    private static <V extends Comparable<V>> ValidationResult checkSmallerThanMin(
            V value, V minValue) {
        final boolean isSmallerThanMin = value != null && minValue != null
                && value.compareTo(minValue) < 0;
        if (isSmallerThanMin) {
            return ValidationResult.error("");
        }
        return ValidationResult.ok();
    }

    private static <V> ValidationResult checkRequired(boolean required, V value,
            V emptyValue) {
        final boolean isRequiredButEmpty = required
                && Objects.equals(emptyValue, value);
        if (isRequiredButEmpty) {
            return ValidationResult.error("");
        }
        return ValidationResult.ok();
    }

    /**
     * Performs a server-side validation of the given value. This is needed
     * because it is possible to circumvent the client side validation
     * constraints using browser development tools.
     */
    private boolean isInvalid(LocalDate value) {
        ValidationResult requiredValidation = checkRequired(required, value,
                getEmptyValue());

        return requiredValidation.isError() || checkValidity(value).isError();
    }

    /**
     * Returns whether the input element has a value or not.
     *
     * @return <code>true</code> if the input element's value is populated,
     *         <code>false</code> otherwise
     */
    @Synchronize(property = "_hasInputValue", value = "has-input-value-changed")
    private boolean isInputValuePresent() {
        return getElement().getProperty("_hasInputValue", false);
    }

    /**
     * Sets displaying a clear button in the datepicker when it has value.
     * <p>
     * The clear button is an icon, which can be clicked to set the datepicker
     * value to {@code null}.
     *
     * @param clearButtonVisible
     *            {@code true} to display the clear button, {@code false} to
     *            hide it
     */
    @Override
    public void setClearButtonVisible(boolean clearButtonVisible) {
        super.setClearButtonVisible(clearButtonVisible);
    }

    /**
     * Gets whether this datepicker displays a clear button when it has value.
     *
     * @return {@code true} if this datepicker displays a clear button,
     *         {@code false} otherwise
     * @see #setClearButtonVisible(boolean)
     */
    public boolean isClearButtonVisible() {
        return super.isClearButtonVisibleBoolean();
    }

    /**
     * Sets the label for the datepicker.
     *
     * @param label
     *            value for the {@code label} property in the datepicker
     */
    @Override
    public void setLabel(String label) {
        super.setLabel(label);
    }

    /**
     * Gets the label of the datepicker.
     *
     * @return the {@code label} property of the datePicker
     */
    @Override
    public String getLabel() {
        return getLabelString();
    }

    @Override
    public void setPlaceholder(String placeholder) {
        super.setPlaceholder(placeholder);
    }

    /**
     * Gets the placeholder of the datepicker.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code placeholder} property of the datePicker
     */
    public String getPlaceholder() {
        return getPlaceholderString();
    }

    /**
     * Date which should be visible when there is no value selected.
     * <p>
     * The same date formats as for the {@code value} property are supported.
     * </p>
     *
     * @param initialPosition
     *            the LocalDate value to set
     */
    public void setInitialPosition(LocalDate initialPosition) {
        setInitialPosition(FORMATTER.apply(initialPosition));
    }

    /**
     * Get the visible date when there is no value selected.
     * <p>
     * The same date formats as for the {@code value} property are supported.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code initialPosition} property from the datepicker
     */
    public LocalDate getInitialPosition() {
        return PARSER.apply(getInitialPositionString());
    }

    @Override
    public void setRequired(boolean required) {
        super.setRequired(required);
        this.required = required;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean required) {
        super.setRequiredIndicatorVisible(required);
        this.required = required;
    }

    /**
     * Determines whether the datepicker is marked as input required.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return {@code true} if the input is required, {@code false} otherwise
     */
    public boolean isRequired() {
        return isRequiredBoolean();
    }

    /**
     * Set the week number visible in the DatePicker.
     * <p>
     * Set true to display ISO-8601 week numbers in the calendar.
     * <p>
     * Notice that displaying week numbers is only supported when
     * i18n.firstDayOfWeek is 1 (Monday).
     *
     * @param weekNumbersVisible
     *            the boolean value to set
     */
    public void setWeekNumbersVisible(boolean weekNumbersVisible) {
        super.setShowWeekNumbers(weekNumbersVisible);
    }

    /**
     * Get the state of {@code showWeekNumbers} property of the datepicker
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code showWeekNumbers} property from the datepicker
     */
    public boolean isWeekNumbersVisible() {
        return isShowWeekNumbersBoolean();
    }

    /**
     * Sets the opened property of the datepicker to open or close its overlay.
     *
     * @param opened
     *            {@code true} to open the datepicker overlay, {@code false} to
     *            close it
     */
    @Override
    public void setOpened(boolean opened) {
        super.setOpened(opened);
    }

    /**
     * Opens the datepicker overlay.
     */
    @Override
    public void open() {
        super.setOpened(true);
    }

    /**
     * Closes the datepicker overlay.
     */
    @Override
    protected void close() {
        super.setOpened(false);
    }

    /**
     * Gets the states of the drop-down for the datepicker
     *
     * @return {@code true} if the drop-down is opened, {@code false} otherwise
     */
    public boolean isOpened() {
        return isOpenedBoolean();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets the name of the DatePicker.
     *
     * @return the {@code name} property from the DatePicker
     */
    public String getName() {
        return getNameString();
    }

    /**
     * When auto open is enabled, the dropdown will open when the field is
     * clicked.
     *
     * @param autoOpen
     *            Value for the auto open property,
     */
    public void setAutoOpen(boolean autoOpen) {
        getElement().setProperty(PROP_AUTO_OPEN_DISABLED, !autoOpen);
    }

    /**
     * When auto open is enabled, the dropdown will open when the field is
     * clicked.
     *
     * @return {@code true} if auto open is enabled. {@code false} otherwise.
     *         Default is {@code true}
     */
    public boolean isAutoOpen() {
        return !getElement().getProperty(PROP_AUTO_OPEN_DISABLED, false);
    }

    /**
     * Performs server-side validation of the current value. This is needed
     * because it is possible to circumvent the client-side validation
     * constraints using browser development tools.
     */
    protected void validate() {
        setInvalid(isInvalid(getValue()));
    }

    @Override
    public Registration addOpenedChangeListener(
            ComponentEventListener<OpenedChangeEvent<DatePicker>> listener) {
        return super.addOpenedChangeListener(listener);
    }

    @Override
    public Registration addInvalidChangeListener(
            ComponentEventListener<InvalidChangeEvent<DatePicker>> listener) {
        return super.addInvalidChangeListener(listener);
    }

    /**
     * The internationalization properties for {@link DatePicker}.
     */
    public static class DatePickerI18n implements Serializable {
        private List<String> monthNames;
        private List<String> weekdays;
        private List<String> weekdaysShort;
        private List<String> dateFormats;
        private int firstDayOfWeek;
        private String week;
        private String calendar;
        private String clear;
        private String today;
        private String cancel;
        private LocalDate referenceDate;

        /**
         * Gets the name of the months.
         *
         * @return the month names
         */
        public List<String> getMonthNames() {
            return monthNames;
        }

        /**
         * Sets the name of the months, starting from January and ending on
         * December.
         *
         * @param monthNames
         *            the month names
         * @return this instance for method chaining
         */
        public DatePickerI18n setMonthNames(List<String> monthNames) {
            this.monthNames = monthNames;
            return this;
        }

        /**
         * Gets the name of the week days.
         *
         * @return the week days
         */
        public List<String> getWeekdays() {
            return weekdays;
        }

        /**
         * Sets the name of the week days, starting from {@code Sunday} and
         * ending on {@code Saturday}.
         *
         * @param weekdays
         *            the week days names
         * @return this instance for method chaining
         */
        public DatePickerI18n setWeekdays(List<String> weekdays) {
            this.weekdays = weekdays;
            return this;
        }

        /**
         * Gets the short names of the week days.
         *
         * @return the short names of the week days
         */
        public List<String> getWeekdaysShort() {
            return weekdaysShort;
        }

        /**
         * Sets the short names of the week days, starting from {@code sun} and
         * ending on {@code sat}.
         *
         * @param weekdaysShort
         *            the short names of the week days
         * @return this instance for method chaining
         */
        public DatePickerI18n setWeekdaysShort(List<String> weekdaysShort) {
            this.weekdaysShort = weekdaysShort;
            return this;
        }

        /**
         * Get the list of custom date formats that are used for formatting the
         * date displayed in the text field, and for parsing the user input
         *
         * @return list of date patterns or null
         */
        public List<String> getDateFormats() {
            return dateFormats;
        }

        /**
         * Sets a custom date format to be used by the date picker. The format
         * is used for formatting the date displayed in the text field, and for
         * parsing the user input.
         * <p>
         * The format is a string pattern using specific symbols to specify how
         * and where the day, month and year should be displayed. The following
         * symbols can be used in the pattern:
         * <ul>
         * <li>{@code yy} - 2 digit year
         * <li>{@code yyyy} - 4 digit year
         * <li>{@code M} - Month, as 1 or 2 digits
         * <li>{@code MM} - Month, padded to 2 digits
         * <li>{@code d} - Day-of-month, as 1 or 2 digits
         * <li>{@code dd} - Day-of-month, padded to 2 digits
         * </ul>
         * <p>
         * For example {@code dd/MM/yyyy}, will format the 20th of June 2021 as
         * {@code 20/06/2021}.
         * <p>
         * Using a custom date format overrides the locale set in the date
         * picker.
         * <p>
         * Setting the format to null will revert the date picker to use the
         * locale for formatting and parsing dates.
         *
         * @param dateFormat
         *            A string with a date format pattern, or null to remove the
         *            previous custom format
         * @return this instance for method chaining
         */
        public DatePickerI18n setDateFormat(String dateFormat) {
            this.setDateFormats(dateFormat);
            return this;
        }

        /**
         * Sets custom date formats to be used by the date picker. The primary
         * format is used for formatting the date displayed in the text field,
         * and for parsing the user input. Additional parsing formats can be
         * specified to support entering dates in multiple formats. The date
         * picker will first attempt to parse the user input using the primary
         * format. If parsing with the primary format fails, it will attempt to
         * parse the input using the additional formats in the order that they
         * were specified. The additional parsing formats are never used for
         * formatting the date. After entering a date using one of the
         * additional parsing formats, it will still be displayed using the
         * primary format.
         * <p>
         * See {@link DatePickerI18n#setDateFormat(String)} on how date patterns
         * are structured.
         * <p>
         * Using custom date formats overrides the locale set in the date
         * picker.
         * <p>
         * Setting the primary format to null will revert the date picker to use
         * the locale for formatting and parsing dates.
         *
         * @param primaryFormat
         *            A string with a date format pattern, or null to remove the
         *            previous custom format
         * @param additionalParsingFormats
         *            Additional date format patterns to be used for parsing
         * @return this instance for method chaining
         */
        public DatePickerI18n setDateFormats(String primaryFormat,
                String... additionalParsingFormats) {
            Objects.requireNonNull(additionalParsingFormats,
                    "Additional parsing formats must not be null");

            if (primaryFormat == null) {
                this.dateFormats = null;
            } else {
                this.dateFormats = new ArrayList<>();
                this.dateFormats.add(primaryFormat);
                this.dateFormats.addAll(Stream.of(additionalParsingFormats)
                        .filter(Objects::nonNull).collect(Collectors.toList()));
            }

            return this;
        }

        /**
         * Gets the first day of the week.
         * <p>
         * 0 for Sunday, 1 for Monday, 2 for Tuesday, 3 for Wednesday, 4 for
         * Thursday, 5 for Friday, 6 for Saturday.
         *
         * @return the index of the first day of the week
         */
        public int getFirstDayOfWeek() {
            return firstDayOfWeek;
        }

        /**
         * Sets the first day of the week.
         * <p>
         * 0 for Sunday, 1 for Monday, 2 for Tuesday, 3 for Wednesday, 4 for
         * Thursday, 5 for Friday, 6 for Saturday.
         *
         * @param firstDayOfWeek
         *            the index of the first day of the week
         * @return this instance for method chaining
         * @throws IllegalArgumentException
         *             if firstDayOfWeek is invalid
         */
        public DatePickerI18n setFirstDayOfWeek(int firstDayOfWeek) {
            if (firstDayOfWeek < 0 || firstDayOfWeek > 6) {
                throw new IllegalArgumentException(
                        "First day of the week needs to be in range of 0 to 6.");
            }
            this.firstDayOfWeek = firstDayOfWeek;
            return this;
        }

        /**
         * Gets the translated word for {@code week}.
         *
         * @return the translated word for week
         */
        public String getWeek() {
            return week;
        }

        /**
         * Sets the translated word for {@code week}.
         *
         * @param week
         *            the translated word for week
         * @return this instance for method chaining
         */
        public DatePickerI18n setWeek(String week) {
            this.week = week;
            return this;
        }

        /**
         * Gets the translated word for {@code calendar}.
         *
         * @return the translated word for calendar
         */
        public String getCalendar() {
            return calendar;
        }

        /**
         * Sets the translated word for {@code calendar}.
         *
         * @param calendar
         *            the translated word for calendar
         * @return this instance for method chaining
         */
        public DatePickerI18n setCalendar(String calendar) {
            this.calendar = calendar;
            return this;
        }

        /**
         * Gets the translated word for {@code clear}.
         *
         * @return the translated word for clear
         */
        public String getClear() {
            return clear;
        }

        /**
         * Sets the translated word for {@code clear}.
         *
         * @param clear
         *            the translated word for clear
         * @return this instance for method chaining
         */
        public DatePickerI18n setClear(String clear) {
            this.clear = clear;
            return this;
        }

        /**
         * Gets the translated word for {@code today}.
         *
         * @return the translated word for today
         */
        public String getToday() {
            return today;
        }

        /**
         * Sets the translated word for {@code today}.
         *
         * @param today
         *            the translated word for today
         * @return this instance for method chaining
         */
        public DatePickerI18n setToday(String today) {
            this.today = today;
            return this;
        }

        /**
         * Gets the translated word for {@code cancel}.
         *
         * @return the translated word for cancel
         */
        public String getCancel() {
            return cancel;
        }

        /**
         * Sets the translated word for {@code cancel}.
         *
         * @param cancel
         *            the translated word for cancel
         * @return this instance for method chaining
         */
        public DatePickerI18n setCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        /**
         * Gets the {@code referenceDate}.
         *
         * @return the reference date
         */
        public LocalDate getReferenceDate() {
            return referenceDate;
        }

        /**
         * Sets the {@code referenceDate}.
         * <p>
         * The reference date is used to determine the century when parsing
         * two-digit years. The century that makes the date closest to the
         * reference date is applied. The default value is the current date.
         * <p>
         * Example: for a reference date of 1970-10-30; years {10, 40, 80}
         * become {2010, 1940, 1980}.
         *
         * @param referenceDate
         *            the date used to base relative dates on
         * @return this instance for method chaining
         */
        public DatePickerI18n setReferenceDate(LocalDate referenceDate) {
            this.referenceDate = referenceDate;
            return this;
        }
    }

    /**
     * Whether the full experience validation is enforced for the component.
     * <p>
     * Exposed with protected visibility to support mocking
     * <p>
     * The method requires the {@code VaadinSession} instance to obtain the
     * application configuration properties, otherwise, the feature is
     * considered disabled.
     *
     * @return {@code true} if enabled, {@code false} otherwise.
     */
    protected boolean isEnforcedFieldValidationEnabled() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null) {
            return false;
        }
        DeploymentConfiguration configuration = session.getConfiguration();
        if (configuration == null) {
            return false;
        }
        return configuration.isEnforcedFieldValidationEnabled();
    }
}
