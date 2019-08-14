package com.xmed.Objects;

/**
 * @author Lior Gur
 */
public enum Difficulty {
    ALL(0),
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int value;

    Difficulty(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
    }


