/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.contextmenu.it;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;

/**
 * @author Vaadin Ltd
 */
@TestPath("vaadin-context-menu/sub-menu-test")
public class SubMenuIT extends AbstractContextMenuIT {

    @Before
    public void init() {
        open();
        verifyClosed();
    }

    @Test
    public void addItemToSubMenu_subMenuRendered_clickListenerWorks() {
        rightClickOn("target");
        verifyNumOfOverlays(1);

        openSubMenu(getMenuItems().get(0));
        verifyNumOfOverlays(2);

        List<TestBenchElement> overlays = getAllOverlays();
        TestBenchElement subItem = getMenuItems(overlays.get(1)).get(0);
        Assert.assertEquals("bar", subItem.getText());

        subItem.click();
        verifyClosed();
        assertMessage("bar");
    }

    @Test
    public void openAndCloseSubMenu_addContent_contentUpdatedAndFunctional() {
        rightClickOn("target");
        openSubMenu(getMenuItems().get(0));
        verifyNumOfOverlays(2);
        clickBody();
        verifyClosed();

        clickElementWithJs("add-item");
        clickElementWithJs("add-item");

        rightClickOn("target");

        openSubMenu(getMenuItems().get(0));
        verifyNumOfOverlays(2);

        List<TestBenchElement> overlays = getAllOverlays();
        List<TestBenchElement> subMenuItems = getMenuItems(overlays.get(1));
        String[] menuItemCaptions = getMenuItemCaptions(subMenuItems);
        Assert.assertArrayEquals(new String[] { "bar", "0", "1" },
                menuItemCaptions);

        subMenuItems.get(1).click();
        verifyClosed();
        assertMessage("0");
    }

    @Test
    public void openAndCloseSubMenu_addSubSubMenu_contentUpdatedAndFunctional() {
        rightClickOn("target");
        openSubMenu(getMenuItems().get(0));
        clickBody();

        clickElementWithJs("add-sub-sub-menu");

        rightClickOn("target");

        openSubMenu(getMenuItems().get(0));
        verifyNumOfOverlays(2);

        openSubMenu(getMenuItems(getAllOverlays().get(1)).get(0));
        verifyNumOfOverlays(3);

        List<TestBenchElement> overlays = getAllOverlays();
        List<TestBenchElement> subMenuItems = getMenuItems(overlays.get(2));
        String[] menuItemCaptions = getMenuItemCaptions(subMenuItems);
        Assert.assertArrayEquals(new String[] { "0" }, menuItemCaptions);

        subMenuItems.get(0).click();
        verifyClosed();
        assertMessage("0");
    }

    @Test
    public void openAndCloseSubMenu_removeAll_noSubMenu_stylesUpdated() {
        rightClickOn("target");
        TestBenchElement parent = getMenuItems().get(0);
        assertHasParentItemClass(parent, true);

        openSubMenu(parent);
        verifyNumOfOverlays(2);

        clickBody();
        verifyClosed();

        clickElementWithJs("remove-all");
        rightClickOn("target");

        parent = getMenuItems().get(0);
        assertHasParentItemClass(parent, false);

        openSubMenu(parent);
        verifyNumOfOverlays(1);
    }

    @Test
    public void componentInsideSubMenu_addComponent_componentIsInSubmenu() {
        findElement(By.id("add-component")).click();
        rightClickOn("target");

        openSubMenu(getMenuItems().get(0));

        verifyNumOfOverlays(2);

        TestBenchElement subMenuOverlay = getAllOverlays().get(1);

        WebElement firstItem = subMenuOverlay.$("vaadin-context-menu-list-box")
                .first().findElement(By.xpath("./*"));

        Assert.assertEquals("a",
                firstItem.getTagName().toLowerCase(Locale.ENGLISH));
        Assert.assertEquals("Link", firstItem.getText());
    }

    @Test
    public void checkableItemInsideSubMenu_addCheckableItem_itemIsInSubmenu() {
        findElement(By.id("add-checkable-component")).click();
        rightClickOn("target");

        openSubMenu(getMenuItems().get(0));

        verifyNumOfOverlays(2);

        TestBenchElement subMenuOverlay = getAllOverlays().get(1);

        WebElement checkableItem = subMenuOverlay
                .$("vaadin-context-menu-list-box").first()
                .findElements(By.xpath("./*")).get(1);

        // verify checkable item
        Assert.assertEquals("vaadin-context-menu-item",
                checkableItem.getTagName().toLowerCase(Locale.ENGLISH));
        Assert.assertEquals("checkable", checkableItem.getText());
        Assert.assertEquals("",
                checkableItem.getAttribute("menu-item-checked"));

        // uncheck the item
        checkableItem.click();

        verifyClosed();

        // We should have a message about selected item:
        assertMessage("Checkable item is false");

        // verify that the item is not checked in UI now
        rightClickOn("target");

        openSubMenu(getMenuItems().get(0));

        verifyNumOfOverlays(2);

        subMenuOverlay = getAllOverlays().get(1);

        checkableItem = subMenuOverlay.$("vaadin-context-menu-list-box").first()
                .findElements(By.xpath("./*")).get(1);

        Assert.assertNull(checkableItem.getAttribute("menu-item-checked"));
    }

    private void assertHasParentItemClass(TestBenchElement item,
            boolean isParent) {
        boolean hasParentClass = item.getClassNames()
                .contains("vaadin-context-menu-parent-item");
        if (isParent) {
            Assert.assertTrue("Item should be styled as the parent item",
                    hasParentClass);
        } else {
            Assert.assertFalse("Item should not be styled as the parent item",
                    hasParentClass);
        }
    }

    @Test
    public void clickParentItem_overlayNotClosed() {
        rightClickOn("target");
        getMenuItems().get(0).click();
        verifyOpened();
    }

    private void assertMessage(String expected) {
        Assert.assertEquals(expected, findElement(By.id("message")).getText());
    }
}
