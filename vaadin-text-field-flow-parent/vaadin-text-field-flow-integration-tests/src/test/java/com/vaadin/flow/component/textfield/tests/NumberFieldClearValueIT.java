/**
 * Copyright 2000-2024 Vaadin Ltd.
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See  {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.textfield.tests;

import static com.vaadin.flow.component.textfield.tests.NumberFieldClearValuePage.CLEAR_AND_SET_VALUE_BUTTON;
import static com.vaadin.flow.component.textfield.tests.NumberFieldClearValuePage.CLEAR_BUTTON;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;

import com.vaadin.flow.component.textfield.testbench.NumberFieldElement;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.tests.AbstractComponentIT;

@TestPath("vaadin-number-field/clear-value")
public class NumberFieldClearValueIT extends AbstractComponentIT {
    private NumberFieldElement numberField;

    private TestBenchElement input;

    @Before
    public void init() {
        open();
        numberField = $(NumberFieldElement.class).first();
        input = numberField.$("input").first();
    }

    @Test
    public void setInputValue_clearValue_inputValueIsEmpty() {
        numberField.sendKeys("1234", Keys.ENTER);
        Assert.assertEquals("1234", input.getPropertyString("value"));

        $("button").id(CLEAR_BUTTON).click();
        Assert.assertEquals("", input.getPropertyString("value"));
    }

    @Test
    public void badInput_setInputValue_clearValue_inputValueIsEmpty() {
        // Native [type=number] inputs don't update their value
        // when you are entering input that the browser is unable to parse,
        // so we have to rely on HTML constraints to determine
        // whether the input element is empty or not.
        numberField.sendKeys("--2", Keys.ENTER);
        Assert.assertEquals("", input.getPropertyString("value"));
        Assert.assertTrue((Boolean) executeScript(
                "return arguments[0].validity.badInput", input));

        $("button").id(CLEAR_BUTTON).click();
        Assert.assertEquals("", input.getPropertyString("value"));
        Assert.assertFalse((Boolean) executeScript(
                "return arguments[0].validity.badInput", input));
    }

    @Test
    public void badInput_setInputValue_clearAndSetValue_inputValueIsPresent() {
        numberField.sendKeys("--2", Keys.ENTER);
        $("button").id(CLEAR_AND_SET_VALUE_BUTTON).click();
        Assert.assertEquals("1234", input.getPropertyString("value"));
    }
}
