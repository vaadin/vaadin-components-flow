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

package com.vaadin.flow.component.avatar.tests;

import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;
import com.vaadin.flow.component.avatar.AvatarGroupVariant;
import com.vaadin.tests.ThemeVariantTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AvatarGroupTest {

    private AvatarGroup avatarGroup;
    private AvatarGroupItem avatarGroupItem1;
    private AvatarGroupItem avatarGroupItem2;

    @Before
    public void setUp() {
        avatarGroup = new AvatarGroup();
        avatarGroupItem1 = new AvatarGroupItem();
        avatarGroupItem2 = new AvatarGroupItem("Foo Bar");
    }

    @Test
    public void setName_getName() {
        avatarGroupItem1.setName("foo bar");
        Assert.assertEquals(avatarGroupItem1.getName(), "foo bar");
    }

    @Test
    public void setAbbr_getAbbr() {
        avatarGroupItem1.setAbbreviation("fb");
        Assert.assertEquals(avatarGroupItem1.getAbbreviation(), "fb");
    }

    @Test
    public void setImgUrl_getImgUrl() {
        avatarGroupItem1.setImage("https://vaadin.com/");
        Assert.assertEquals(avatarGroupItem1.getImage(), "https://vaadin.com/");
    }

    @Test
    public void setColorIndex_getColorIndex() {
        avatarGroupItem1.setColorIndex(3);
        Assert.assertEquals(avatarGroupItem1.getColorIndex(), (Integer) 3);
    }

    @Test
    public void setCreatedItems_getCreatedItems() {
        List<AvatarGroupItem> items = List.of(avatarGroupItem1,
                avatarGroupItem2);
        avatarGroup.setItems(items);

        Assert.assertEquals(items, avatarGroup.getItems());
    }

    @Test
    public void getEmptyItems_doesNotThrow() {
        avatarGroup.getItems();
    }

    @Test
    public void createWithItems_getCreatedItems() {
        List<AvatarGroupItem> items = List.of(avatarGroupItem1,
                avatarGroupItem2);
        AvatarGroup createdAvatarGroup = new AvatarGroup(items);

        Assert.assertEquals(items, createdAvatarGroup.getItems());
    }

    @Test
    public void createWithVarargsItems_getCreatedItems() {
        AvatarGroup createdAvatarGroup = new AvatarGroup(avatarGroupItem1,
                avatarGroupItem2);

        Assert.assertEquals(List.of(avatarGroupItem1, avatarGroupItem2),
                createdAvatarGroup.getItems());
    }

    @Test
    public void addItems_getItems() {
        avatarGroup.setItems(List.of(avatarGroupItem1, avatarGroupItem2));
        AvatarGroupItem addedItem = new AvatarGroupItem("Bar Baz");
        avatarGroup.add(addedItem);

        Assert.assertEquals(
                List.of(avatarGroupItem1, avatarGroupItem2, addedItem),
                avatarGroup.getItems());
    }

    @Test
    public void removeItems_getItems() {
        avatarGroup.setItems(List.of(avatarGroupItem1, avatarGroupItem2));
        avatarGroup.remove(avatarGroupItem2);

        Assert.assertEquals(List.of(avatarGroupItem1), avatarGroup.getItems());
    }

    @Test
    public void setMaxItemsVisible_getMaxItemsVisible() {
        avatarGroup.setMaxItemsVisible(3);

        Assert.assertEquals((Integer) 3, avatarGroup.getMaxItemsVisible());
    }

    @Test
    public void getMaxItemsVisible_returnsNull() {
        Assert.assertNull(avatarGroup.getMaxItemsVisible());
    }

    @Test
    public void addThemeVariant_themeAttributeContainsThemeVariant() {
        ThemeVariantTestHelper.addThemeVariant_themeNamesContainsThemeVariant(
                new AvatarGroup(), AvatarGroupVariant.LUMO_LARGE);
    }

    @Test
    public void addThemeVariant_removeTheme_doesNotContainThemeVariant() {
        ThemeVariantTestHelper
                .addThemeVariant_removeThemeVariant_themeNamesDoesNotContainThemeVariant(
                        new AvatarGroup(), AvatarGroupVariant.LUMO_LARGE);
    }

    @Test
    public void setI18n() {
        AvatarGroup.AvatarGroupI18n i18n = new AvatarGroup.AvatarGroupI18n()
                .setAnonymous("anonyymi")
                .setOneActiveUser("Yksi käyttäjä aktiivinen")
                .setManyActiveUsers("{count} aktiivista käyttäjää");

        avatarGroup.setI18n(i18n);
        Assert.assertEquals(i18n, avatarGroup.getI18n());
    }

}
