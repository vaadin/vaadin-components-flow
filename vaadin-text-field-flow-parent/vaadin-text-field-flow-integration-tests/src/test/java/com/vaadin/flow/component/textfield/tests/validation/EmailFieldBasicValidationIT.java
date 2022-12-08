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
package com.vaadin.flow.component.textfield.tests.validation;

import org.junit.Test;
import org.openqa.selenium.Keys;

import com.vaadin.flow.component.textfield.testbench.EmailFieldElement;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.tests.validation.AbstractValidationIT;

import static com.vaadin.flow.component.textfield.tests.validation.EmailFieldBasicValidationPage.MIN_LENGTH_INPUT;
import static com.vaadin.flow.component.textfield.tests.validation.EmailFieldBasicValidationPage.MAX_LENGTH_INPUT;
import static com.vaadin.flow.component.textfield.tests.validation.EmailFieldBasicValidationPage.PATTERN_INPUT;
import static com.vaadin.flow.component.textfield.tests.validation.EmailFieldBasicValidationPage.REQUIRED_BUTTON;

@TestPath("vaadin-email-field/validation/basic")
public class EmailFieldBasicValidationIT
        extends AbstractValidationIT<EmailFieldElement> {
    @Test
    public void fieldIsInitiallyValid() {
        assertClientValid();
        assertServerValid();
    }

    @Test
    public void triggerBlur_assertValidity() {
        testField.sendKeys(Keys.TAB);
        assertServerValid();
        assertClientValid();
    }

    @Test
    public void clientSideInvalidStateIsNotPropagatedToServer() {
        // Make the field invalid
        $("button").id(REQUIRED_BUTTON).click();
        testField.sendKeys(Keys.TAB);

        executeScript("arguments[0].invalid = false", testField);

        assertServerInvalid();
    }

    @Test
    public void required_triggerBlur_assertValidity() {
        $("button").id(REQUIRED_BUTTON).click();

        testField.sendKeys(Keys.TAB);
        assertServerInvalid();
        assertClientInvalid();
    }

    @Test
    public void required_changeValue_assertValidity() {
        $("button").id(REQUIRED_BUTTON).click();

        testField.setValue("john@vaadin.com");
        assertServerValid();
        assertClientValid();

        testField.setValue("");
        assertServerInvalid();
        assertClientInvalid();
    }

    @Test
    public void required_clientSidePropertyIsNotPropagatedToServer() {
        $("button").id(REQUIRED_BUTTON).click();

        executeScript("arguments[0].required = false", testField);

        testField.sendKeys(Keys.TAB);
        assertServerInvalid();
    }

    @Test
    public void minLength_triggerBlur_assertValidity() {
        $("input").id(MIN_LENGTH_INPUT).sendKeys("13", Keys.ENTER);

        testField.sendKeys(Keys.TAB);
        assertServerValid();
        assertClientValid();
    }

    @Test
    public void minLength_changeValue_assertValidity() {
        $("input").id(MIN_LENGTH_INPUT).sendKeys("13", Keys.ENTER);

        testField.setValue("a@vaadin.com");
        assertClientInvalid();
        assertServerInvalid();

        testField.setValue("aa@vaadin.com");
        assertClientValid();
        assertServerValid();

        testField.setValue("aaa@vaadin.com");
        assertClientValid();
        assertServerValid();
    }

    @Test
    public void minLength_clientSidePropertyIsNotPropagatedToServer() {
        $("input").id(MIN_LENGTH_INPUT).sendKeys("13", Keys.ENTER);

        executeScript("arguments[0].minLength = 0", testField);

        testField.setValue("a@vaadin.com");
        assertServerInvalid();
    }

    @Test
    public void maxLength_changeValue_assertValidity() {
        $("input").id(MAX_LENGTH_INPUT).sendKeys("13", Keys.ENTER);

        testField.setValue("aaa@vaadin.com");
        assertClientInvalid();
        assertServerInvalid();

        testField.setValue("aa@vaadin.com");
        assertClientValid();
        assertServerValid();

        testField.setValue("a@vaadin.com");
        assertClientValid();
        assertServerValid();
    }

    @Test
    public void maxLength_clientSidePropertyIsNotPropagatedToServer() {
        $("input").id(MAX_LENGTH_INPUT).sendKeys("13", Keys.ENTER);

        executeScript("arguments[0].maxLength = 130", testField);

        testField.setValue("aaa@vaadin.com");
        assertServerInvalid();
    }

    @Test
    public void defaultPattern_triggerBlur_assertValidity() {
        testField.sendKeys(Keys.TAB);
        assertServerValid();
        assertClientValid();
    }

    @Test
    public void defaultPattern_changeValue_assertValidity() {
        testField.setValue("arbitrary string");
        assertClientInvalid();
        assertServerInvalid();

        testField.setValue("john@vaadin.com");
        assertClientValid();
        assertServerValid();
    }

    @Test
    public void pattern_changeValue_assertValidity() {
        $("input").id(PATTERN_INPUT).sendKeys("^[^\\d]+@vaadin.com$",
                Keys.ENTER);

        testField.setValue("2222@vaadin.com");
        assertClientInvalid();
        assertServerInvalid();

        testField.setValue("john@vaadin.com");
        assertClientValid();
        assertServerValid();
    }

    @Test
    public void detach_attach_preservesInvalidState() {
        // Make field invalid
        $("button").id(REQUIRED_BUTTON).click();
        testField.sendKeys(Keys.TAB);

        detachAndReattachField();

        assertServerInvalid();
        assertClientInvalid();
    }

    @Test
    public void webComponentCanNotModifyInvalidState() {
        assertWebComponentCanNotModifyInvalidState();

        detachAndReattachField();

        assertWebComponentCanNotModifyInvalidState();
    }

    protected EmailFieldElement getTestField() {
        return $(EmailFieldElement.class).first();
    }
}
