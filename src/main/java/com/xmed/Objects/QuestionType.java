package com.xmed.Objects;

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

    //WRONG,
    //CORRECT,
    //UNSOLVED,
    //MARKED
}
