/**
 * Copyright (C) 2000-2024 Vaadin Ltd
 *
 * This program is available under Vaadin Commercial License and Service Terms.
 *
 * See <https://vaadin.com/commercial-license-and-service-terms> for the full
 * license.
 */
package com.vaadin.flow.theme.lumo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.Route;

@Tag("explicit-lumo-themed-template")
@JsModule("./src/LumoThemedTemplate.js")
@Route(value = "vaadin-lumo-theme/explicit-template-view")
/*
 * Note that this is using component instead of polymer template, because
 * otherwise the themed module would have to import the original /src module,
 * and that would make testing the actual feature here (theme rewrite) more
 * complex.
 */
public class ExplicitLumoTemplateView extends Component {

}
