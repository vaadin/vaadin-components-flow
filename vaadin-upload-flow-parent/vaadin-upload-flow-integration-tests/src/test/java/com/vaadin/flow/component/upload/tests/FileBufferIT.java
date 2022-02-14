package com.vaadin.flow.component.upload.tests;

import com.vaadin.flow.component.upload.testbench.UploadElement;
import com.vaadin.flow.testutil.TestPath;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Tests for Upload using FileBuffer and MultiFileBuffer.
 */
@TestPath("vaadin-upload/filebuffer")
public class FileBufferIT extends AbstractUploadIT {
    @Test
    public void testUploadAnyFile() throws Exception {
        open();
        final UploadElement upload = $(UploadElement.class).id("single-upload");
        waitUntil(driver -> upload.isDisplayed());

        File tempFile = createTempFile();
        upload.upload(tempFile);

        WebElement uploadOutput = getDriver()
                .findElement(By.id("single-upload-output"));

        String content = uploadOutput.getText();

        String expectedContent = tempFile.getName() + getTempFileContents();

        Assert.assertEquals("Upload content does not match expected",
                expectedContent, content);
    }

}
