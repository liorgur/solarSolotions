package com.xmed.models.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TestType {
    MISTAKES("Wrongs"),
    UNSEEN("Unseen"),
    SURPRISE("Surprise"),
    CUSTOM("Custom");

    private final String value;

    TestType(final String newValue) {
        value = newValue;
    }

    @JsonValue
    public String getValue() { return value; }
}
