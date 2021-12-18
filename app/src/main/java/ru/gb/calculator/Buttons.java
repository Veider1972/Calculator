package ru.gb.calculator;

public enum Buttons {
    ZERO("0"),ONE("1"),TWO("2"),THREE("3"),FOUR("4"),FIVE("5"),
    SIX("6"),SEVEN("7"),EIGHT("8"),NINE("9"),
    DOT("."),NEGATIVE("-"),
    RESET(null),
    PUT_MEMORY(null), GET_MEMORY(null),
    PLUS("+"),MINUS("-"),MULTIPLY("*"),DIVIDE("/"),
    EQUAL("=");

    private final String value;

    public String getValue() {
        return value;
    }

    Buttons(String value) {
        this.value = value;
    }
}
