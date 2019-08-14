package com.xmed.Objects.Enums;

public enum QuestionType {
    WRONG('W'),
    CORRECT('C'),
    UNSOLVED('U'),
    MARKED('M');

    private final char value;

    QuestionType(final char newValue) {
        value = newValue;
    }

    public char getValue() { return value; }
}
