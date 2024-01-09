/*
 * Copyright 2000-2024 Vaadin Ltd.
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
package com.vaadin.flow.component.shared;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Tests for {@link SelectionPreservationHandler}.
 */
public class SelectionPreservationHandlerTest {

    private SelectionPreservationStrategy selectionPreservationStrategy;

    private SelectionPreservationHandler<String> selectionPreservationHandler;

    @Before
    public void setup() {
        selectionPreservationHandler = new SelectionPreservationHandler<>(
                SelectionPreservationStrategy.DISCARD) {
            @Override
            public void onPreserveAll(DataChangeEvent<String> dataChangeEvent) {
                selectionPreservationStrategy = SelectionPreservationStrategy.PRESERVE_ALL;
            }

            @Override
            public void onPreserveExisting(
                    DataChangeEvent<String> dataChangeEvent) {
                selectionPreservationStrategy = SelectionPreservationStrategy.PRESERVE_EXISTING;
            }

            @Override
            public void onDiscard(DataChangeEvent<String> dataChangeEvent) {
                selectionPreservationStrategy = SelectionPreservationStrategy.DISCARD;
            }
        };
    }

    @Test
    public void runHandler_handlerUsesDefaultStrategy() {
        selectionPreservationHandler.handleDataChange(
                new DataChangeEvent<>(DataProvider.ofItems()));
        Assert.assertEquals(SelectionPreservationStrategy.DISCARD,
                selectionPreservationStrategy);
    }

    @Test
    public void updateStrategy_runHandler_handlerUsesCorrectStrategy() {
        List.of(SelectionPreservationStrategy.PRESERVE_ALL,
                SelectionPreservationStrategy.PRESERVE_EXISTING)
                .forEach(strategyToSet -> {
                    selectionPreservationHandler
                            .setSelectionPreservationStrategy(strategyToSet);
                    selectionPreservationHandler.handleDataChange(
                            new DataChangeEvent<>(DataProvider.ofItems()));
                    Assert.assertEquals(strategyToSet,
                            selectionPreservationStrategy);
                });
    }

    @Test
    public void setStrategyNull_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class,
                () -> selectionPreservationHandler
                        .setSelectionPreservationStrategy(null));
    }
}
