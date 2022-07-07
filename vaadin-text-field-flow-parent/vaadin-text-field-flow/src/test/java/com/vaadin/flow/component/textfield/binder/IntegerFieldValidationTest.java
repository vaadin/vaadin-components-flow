package com.vaadin.flow.component.textfield.binder;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.function.SerializablePredicate;

public class IntegerFieldValidationTest
        extends AbstractTextFieldValidationTest<Integer> {

    private IntegerField field;

    @Override
    protected HasValue<?, Integer> getField() {
        if (field == null) {
            field = new IntegerField();
            field.setMax(10);
        }
        return field;
    }

    @Override
    protected void setValidValue() {
        getField().setValue(5);
    }

    @Override
    protected void setComponentInvalidValue() {
        getField().setValue(15);
    }

    @Override
    protected void setBinderInvalidValue() {
        getField().setValue(1);
    }

    @Override
    protected SerializablePredicate<? super Integer> getValidator() {
        return value -> value == null || value > 2;
    }
}
