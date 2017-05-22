package com.iurii.microservice.model;

import java.util.Arrays;

public enum Mode {
    SET("set"), UPDATE("update"), UPDATE_AMOUNT("updateAmount");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

    public static Mode findModeByName(String name) {
        return Arrays.stream(values()).filter(enumValue -> enumValue.name.equals(name)).findAny().orElse(null);
    }
    @Override
    public String toString() {
        return name;
    }
}
