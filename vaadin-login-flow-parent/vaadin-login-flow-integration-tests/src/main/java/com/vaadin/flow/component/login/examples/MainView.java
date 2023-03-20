/**
 * Copyright (C) 2000-2023 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.component.login.examples;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;

@Route(value = "vaadin-login")
public class MainView extends AbstractView {

    private LoginForm loginForm = new LoginForm();

    public MainView() {
        init(loginForm);
    }

}
