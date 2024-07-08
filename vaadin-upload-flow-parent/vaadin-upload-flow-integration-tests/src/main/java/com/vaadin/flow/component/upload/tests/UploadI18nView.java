/*
 * Copyright (C) 2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See {@literal <https://vaadin.com/commercial-license-and-service-terms>}  for the full
 * license.
 */
package com.vaadin.flow.component.upload.tests;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

@Route("vaadin-upload/i18n")
public class UploadI18nView extends Div {
    public UploadI18nView() {
        createFullUploadI18N();
        createPartialUploadI18N();
        createDetachReattachUpload();
    }

    private void createFullUploadI18N() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setId("upload-full-i18n");

        upload.setI18n(UploadTestsI18N.RUSSIAN_FULL);

        add(new H1("Full I18N"), upload, new Hr());
    }

    private void createPartialUploadI18N() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setId("upload-partial-i18n");

        upload.setI18n(UploadTestsI18N.RUSSIAN_PARTIAL);

        add(new H1("Partial I18N"), upload, new Hr());
    }

    private void createDetachReattachUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setId("upload-detach-reattach-i18n");

        NativeButton btnToggleAttached = new NativeButton("Toggle attached",
                e -> {
                    if (upload.getParent().isPresent()) {
                        remove(upload);
                    } else {
                        add(upload);
                    }
                });
        btnToggleAttached.setId("btn-toggle-attached");

        NativeButton btnSetI18n = new NativeButton("Set i18n", e -> {
            upload.setI18n(UploadTestsI18N.RUSSIAN_FULL);
        });
        btnSetI18n.setId("btn-set-i18n");

        add(new H1("Detach / reattach"), upload, btnToggleAttached, btnSetI18n);
    }

}
