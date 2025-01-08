/*
 * Copyright 2000-2025 Vaadin Ltd.
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
package com.vaadin.flow.component.splitlayout.testbench;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

/**
 * A TestBench element representing a <code>&lt;vaadin-split-layout&gt;</code>
 * element.
 */
@Element("vaadin-split-layout")
public class SplitLayoutElement extends TestBenchElement {

    public TestBenchElement getSplitter() {
        return getPropertyElement("$", "splitter");
    }

    public TestBenchElement getPrimaryComponent() {
        return (TestBenchElement) executeScript(
                "return arguments[0].children[0]", this);
    }

    public TestBenchElement getSecondaryComponent() {
        return (TestBenchElement) executeScript(
                "return arguments[0].children[1]", this);
    }
}
