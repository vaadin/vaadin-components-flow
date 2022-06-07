package com.vaadin.flow.component.combobox.test;

import com.vaadin.flow.component.combobox.testbench.MultiSelectComboBoxElement;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.tests.AbstractComponentIT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

@TestPath("vaadin-multi-select-combo-box/polymer-wrapper")
public class MultiSelectComboBoxPolymerWrapperIT extends AbstractComponentIT {
    private MultiSelectComboBoxElement comboBox;

    @Before
    public void init() {
        open();
        TestBenchElement polymerWrapper = $(
                "multi-select-combo-box-polymer-wrapper").waitForFirst();
        comboBox = polymerWrapper.$(MultiSelectComboBoxElement.class).first();
    }

    @Test
    public void addWithPolymerTemplate_noErrors() {
        checkLogsForErrors();
    }

    // Basic smoke test to verify that connector works correctly
    @Test
    public void openPopup_showsAllItems() {
        comboBox.openPopup();
        comboBox.waitForLoadingFinished();

        List<String> options = comboBox.getOptions();
        Assert.assertEquals(100, options.size());
        Assert.assertTrue(
                options.containsAll(Set.of("Item 1", "Item 10", "Item 20")));
    }
}
