package com.iurii.microservice.service.binders;

import com.iurii.microservice.model.Mode;

import java.beans.PropertyEditorSupport;

public class ModeBinder extends PropertyEditorSupport {

    @Override
    public void setAsText(final String name) {
        setValue(Mode.findModeByName(name));
    }
}
