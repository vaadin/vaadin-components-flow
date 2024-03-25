/**
 * Copyright (C) 2000-2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.progressbar;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

/**
 * <p>
 * Description copied from corresponding location in WebComponent:
 * </p>
 * <p>
 * {@code <vaadin-progress-bar>} is a Web Component for progress bars.
 * </p>
 * <p>
 * &lt;vaadin-progress-bar min=&quot;0&quot; max=&quot;1&quot;
 * value=&quot;0.5&quot;&gt; &lt;/vaadin-progress-bar&gt;
 * </p>
 * <h3>Styling</h3>
 * <p>
 * The following shadow DOM parts are available for styling:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Part name</th>
 * <th>Description</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code bar}</td>
 * <td>Progress-bar's background</td>
 * </tr>
 * <tr>
 * <td>{@code value}</td>
 * <td>Progress-bar's foreground</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * See
 * <a href="https://github.com/vaadin/vaadin-themable-mixin/wiki">ThemableMixin
 * – how to apply styles for shadow parts</a>
 * </p>
 * <p>
 * The following custom properties are available:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Custom property</th>
 * <th>Description</th>
 * <th>Default</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code --vaadin-progress-value}</td>
 * <td>current progress value (between 0 and 1)</td>
 * <td>0</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * The following state attributes are available for styling:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Attribute</th>
 * <th>Description</th>
 * <th>Part name</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code indeterminate}</td>
 * <td>Set to an indeterminate progress bar</td>
 * <td>:host</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @deprecated since v23.3, generated classes will be removed in v24.
 */
@Deprecated
@Tag("vaadin-progress-bar")
@NpmPackage(value = "@vaadin/polymer-legacy-adapter", version = "23.4.0")
@JsModule("@vaadin/polymer-legacy-adapter/style-modules.js")
@JsModule("@vaadin/progress-bar/src/vaadin-progress-bar.js")
public abstract class GeneratedVaadinProgressBar<R extends GeneratedVaadinProgressBar<R>>
        extends Component implements HasStyle, HasTheme {

    /**
     * Adds theme variants to the component.
     *
     * @param variants
     *            theme variants to add
     *
     * @deprecated since v23.3, generated classes will be removed in v24. Use
     *             {@link ProgressBar#addThemeVariants} instead.
     */
    @Deprecated
    public void addThemeVariants(ProgressBarVariant... variants) {
        getThemeNames().addAll(
                Stream.of(variants).map(ProgressBarVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * Removes theme variants from the component.
     *
     * @param variants
     *            theme variants to remove
     *
     * @deprecated since v23.3, generated classes will be removed in v24. Use
     *             {@link ProgressBar#removeThemeVariants} instead.
     */
    @Deprecated
    public void removeThemeVariants(ProgressBarVariant... variants) {
        getThemeNames().removeAll(
                Stream.of(variants).map(ProgressBarVariant::getVariantName)
                        .collect(Collectors.toList()));
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Current progress value.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code value} property from the webcomponent
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected double getValueDouble() {
        return getElement().getProperty("value", 0.0);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Current progress value.
     * </p>
     *
     * @param value
     *            the double value to set
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected void setValue(double value) {
        getElement().setProperty("value", value);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Minimum bound of the progress bar.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code min} property from the webcomponent
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected double getMinDouble() {
        return getElement().getProperty("min", 0.0);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Minimum bound of the progress bar.
     * </p>
     *
     * @param min
     *            the double value to set
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected void setMin(double min) {
        getElement().setProperty("min", min);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Maximum bound of the progress bar.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code max} property from the webcomponent
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected double getMaxDouble() {
        return getElement().getProperty("max", 0.0);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Maximum bound of the progress bar.
     * </p>
     *
     * @param max
     *            the double value to set
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected void setMax(double max) {
        getElement().setProperty("max", max);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Indeterminate state of the progress bar. This property takes precedence
     * over other state properties (min, max, value).
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code indeterminate} property from the webcomponent
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected boolean isIndeterminateBoolean() {
        return getElement().getProperty("indeterminate", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Indeterminate state of the progress bar. This property takes precedence
     * over other state properties (min, max, value).
     * </p>
     *
     * @param indeterminate
     *            the boolean value to set
     *
     * @deprecated since v23.3, generated classes will be removed in v24.
     */
    @Deprecated
    protected void setIndeterminate(boolean indeterminate) {
        getElement().setProperty("indeterminate", indeterminate);
    }
}
