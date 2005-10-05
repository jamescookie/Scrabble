package com.jamescookie.scrabble;

public class Operator {
    public static Operator EQUALS = new Operator("Equal To", 1);
    public static Operator LESS_THAN = new Operator("Less Than", 2);
    public static Operator GREATER_THAN = new Operator("Greater Than", 3);

    private final String code;
    private final int value;

    private Operator(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator)) return false;

        final Operator operator = (Operator) o;

        return !(code != null ? !code.equals(operator.code) : operator.code != null);

    }

    public int hashCode() {
        return (code != null ? code.hashCode() : 0);
    }

    public static Operator findOperator(int value) {
        Operator retValue = null;

        if (value == EQUALS.getValue()) {
            retValue = EQUALS;
        } else if (value == LESS_THAN.getValue()) {
            retValue = LESS_THAN;
        } else if (value == GREATER_THAN.getValue()) {
            retValue = GREATER_THAN;
        }

        return retValue;
    }
}
