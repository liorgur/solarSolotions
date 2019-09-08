package com.xmed.models.Enums;

public enum QuestionType {
    ALL('A'),
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
