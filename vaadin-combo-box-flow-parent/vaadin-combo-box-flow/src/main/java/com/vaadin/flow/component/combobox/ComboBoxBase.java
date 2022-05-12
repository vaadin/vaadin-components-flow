package com.vaadin.flow.component.combobox;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.component.combobox.events.CustomValueSetEvent;
import com.vaadin.flow.shared.Registration;

/**
 * Provides base functionality for combo box related components, such as {@link ComboBox}
 *
 * @param <TComponent> Type of the component that extends from this class
 * @param <TItem>      Type of individual items that are selectable in the combo box
 * @param <TValue>     Type of the selection / value of the extending component
 */
public class ComboBoxBase<TComponent extends ComboBoxBase<TComponent, TItem, TValue>, TItem, TValue> extends AbstractSinglePropertyField<TComponent, TValue> implements HasStyle, Focusable<TComponent> {

    private static final String PROP_AUTO_OPEN_DISABLED = "autoOpenDisabled";

    /**
     * Constructs a new ComboBoxBase instance
     *
     * @param valuePropertyName   name of the value property of the web component that should be used to set values, or listen to value changes
     * @param defaultValue        the default value of the component
     * @param valuePropertyType   the class that represents the type of the raw value of the Flow element property
     * @param presentationToModel a function to convert a raw property value into a value using the user-specified model type
     * @param modelToPresentation a function to convert a value using the user-specified model type into a raw property value
     * @param <TValueProperty>    the type of the raw value of the Flow element property
     */
    public <TValueProperty> ComboBoxBase(String valuePropertyName,
                                         TValue defaultValue,
                                         Class<TValueProperty> valuePropertyType,
                                         SerializableBiFunction<TComponent, TValueProperty, TValue> presentationToModel,
                                         SerializableBiFunction<TComponent, TValue, TValueProperty> modelToPresentation) {
        super(valuePropertyName, defaultValue, valuePropertyType, presentationToModel,
                modelToPresentation);
    }

    /**
     * Whether the component should automatically receive focus when the page loads.
     */
    public boolean isAutofocus() {
        return getElement().getProperty("autofocus", false);
    }

    /**
     * Sets the whether the component should automatically receive focus when the page loads. Defaults to {@code false}.
     */
    public void setAutofocus(boolean autofocus) {
        getElement().setProperty("autofocus", autofocus);
    }

    /**
     * Sets the page size, which is the number of items requested at a time from
     * the data provider. This does not guarantee a maximum query size to the
     * backend; when the overlay has room to render more new items than the page
     * size, multiple "pages" will be requested at once.
     * <p>
     * The page size is also the largest number of items that can support
     * client-side filtering. If you provide more items than the page size, the
     * component has to fall back to server-side filtering.
     * <p>
     * Setting the page size after the ComboBox has been rendered effectively
     * resets the component, and the current page(s) and sent over again.
     * <p>
     * The default page size is 50.
     *
     * @param pageSize the maximum number of items sent per request, should be
     *                 greater than zero
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException(
                    "Page size should be greater than zero.");
        }
        getElement().setProperty("pageSize", pageSize);
    }

    /**
     * Gets the page size, which is the number of items fetched at a time from
     * the data provider.
     * <p>
     * The page size is also the largest number of items that can support
     * client-side filtering. If you provide more items than the page size, the
     * component has to fall back to server-side filtering.
     * <p>
     * The default page size is 50.
     *
     * @return the maximum number of items sent per request
     * @see #setPageSize(int)
     */
    public int getPageSize() {
        return getElement().getProperty("pageSize", 50);
    }

    /**
     * Whether the dropdown is opened or not.
     *
     * @return {@code true} if the drop-down is opened, {@code false} otherwise
     */
    @Synchronize(property = "opened", value = "opened-changed")
    public boolean isOpened() {
        return getElement().getProperty("opened", false);
    }

    /**
     * Sets whether the dropdown should be opened or not.
     */
    public void setOpened(boolean opened) {
        getElement().setProperty("opened", opened);
    }

    /**
     * If {@code true}, the user can input string values that do not match to
     * any existing item labels, which will fire a {@link CustomValueSetEvent}.
     *
     * @return {@code true} if the component fires custom value set events,
     *         {@code false} otherwise
     * @see #setAllowCustomValue(boolean)
     * @see #addCustomValueSetListener(ComponentEventListener)
     */
    public boolean isAllowCustomValue() {
        return getElement().getProperty("allowCustomValue", false);
    }

    /**
     * Enables or disables the component firing events for custom string input.
     * <p>
     * When enabled, a {@link CustomValueSetEvent} will be fired when the user
     * inputs a string value that does not match any existing items and commits
     * it eg. by blurring or pressing the enter-key.
     * <p>
     * Note that ComboBox doesn't do anything with the custom value string
     * automatically. Use the
     * {@link #addCustomValueSetListener(ComponentEventListener)} method to
     * determine how the custom value should be handled. For example, when the
     * ComboBox has {@code String} as the value type, you can add a listener
     * which sets the custom string as the value of the ComboBox with
     * {@link #setValue(Object)}.
     * <p>
     * Setting to {@code true} also allows an unfocused ComboBox to display a
     * string that doesn't match any of its items nor its current value, unless
     * this is explicitly handled with
     * {@link #addCustomValueSetListener(ComponentEventListener)}. When set to
     * {@code false}, an unfocused ComboBox will always display the label of the
     * currently selected item.
     *
     * @param allowCustomValue {@code true} to enable custom value set events, {@code false}
     *                         to disable them
     * @see #addCustomValueSetListener(ComponentEventListener)
     */
    public void setAllowCustomValue(boolean allowCustomValue) {
        getElement().setProperty("allowCustomValue", allowCustomValue);
    }

    /**
     * Filtering string the user has typed into the input field.
     */
    @Synchronize(property = "filter", value = "filter-changed")
    protected String getFilter() {
        return getElement().getProperty("filter");
    }

    /**
     * Sets the filter string for the filter input.
     * <p>
     * Setter is only required to allow using @Synchronize
     *
     * @param filter the String value to set
     */
    protected void setFilter(String filter) {
        getElement().setProperty("filter", filter == null ? "" : filter);
    }

    /**
     * Whether the component has an invalid value or not.
     */
    @Synchronize(property = "invalid", value = "invalid-changed")
    public boolean isInvalid() {
        return getElement().getProperty("invalid", false);
    }

    /**
     * Sets whether the component has an invalid value or not.
     */
    public void setInvalid(boolean invalid) {
        getElement().setProperty("invalid", invalid);
    }

    /**
     * Whether the component requires a value to be considered in a valid state.
     */
    public void setRequired(boolean required) {
        super.setRequiredIndicatorVisible(required);
    }

    /**
     * Sets whether the component requires a value to be considered in a valid state.
     */
    public boolean isRequired() {
        return super.isRequiredIndicatorVisible();
    }

    /**
     * Whether the component should block user input that does not match the configured pattern
     */
    public boolean isPreventInvalidInput() {
        return getElement().getProperty("preventInvalidInput", false);
    }

    /**
     * Sets whether the component should block user input that does not match the configured pattern
     */
    public void setPreventInvalidInput(boolean preventInvalidInput) {
        getElement().setProperty("preventInvalidInput", preventInvalidInput);
    }

    /**
     * The pattern to validate the input with
     */
    public String getPattern() {
        return getElement().getProperty("pattern");
    }

    /**
     * Sets the pattern with which to validate the input
     */
    public void setPattern(String pattern) {
        getElement().setProperty("pattern", pattern == null ? "" : pattern);
    }

    /**
     * The error message that should be displayed when the component becomes invalid
     */
    public String getErrorMessage() {
        return getElement().getProperty("errorMessage");
    }

    /**
     * Sets the error message that should be displayed when the component becomes invalid
     */
    public void setErrorMessage(String errorMessage) {
        getElement().setProperty("errorMessage",
                errorMessage == null ? "" : errorMessage);
    }

    /**
     * The placeholder text that should be displayed in the input element, when the user has not entered a value
     */
    public String getPlaceholder() {
        return getElement().getProperty("placeholder");
    }

    /**
     * Sets the placeholder text that should be displayed in the input element, when the user has not entered a value
     */
    public void setPlaceholder(String placeholder) {
        getElement().setProperty("placeholder",
                placeholder == null ? "" : placeholder);
    }

    /**
     * Enables or disables the dropdown opening automatically. If {@code false}
     * the dropdown is only opened when clicking the toggle button or pressing
     * Up or Down arrow keys.
     *
     * @param autoOpen {@code false} to prevent the dropdown from opening
     *                 automatically
     */
    public void setAutoOpen(boolean autoOpen) {
        getElement().setProperty(PROP_AUTO_OPEN_DISABLED, !autoOpen);
    }

    /**
     * Gets whether dropdown will open automatically or not.
     *
     * @return @{code true} if enabled, {@code false} otherwise
     */
    public boolean isAutoOpen() {
        return !getElement().getProperty(PROP_AUTO_OPEN_DISABLED, false);
    }

    /**
     * Adds a listener for the event which is fired when user inputs a string
     * value that does not match any existing items and commits it eg. by
     * blurring or pressing the enter-key.
     * <p>
     * Note that ComboBox doesn't do anything with the custom value string
     * automatically. Use this method to determine how the custom value should
     * be handled. For example, when the ComboBox has {@code String} as the
     * value type, you can add a listener which sets the custom string as the
     * value of the ComboBox with {@link #setValue(Object)}.
     * <p>
     * As a side effect, this makes the ComboBox allow custom values. If you
     * want to disable the firing of custom value set events once the listener
     * is added, please disable it explicitly via the
     * {@link #setAllowCustomValue(boolean)} method.
     * <p>
     * The custom value becomes disallowed automatically once the last custom
     * value set listener is removed.
     *
     * @param listener
     *            the listener to be notified when a new value is filled
     * @return a {@link Registration} for removing the event listener
     * @see #setAllowCustomValue(boolean)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Registration addCustomValueSetListener(
            ComponentEventListener<CustomValueSetEvent<TComponent>> listener) {
        return addListener(CustomValueSetEvent.class,
                (ComponentEventListener) listener);
    }
}
