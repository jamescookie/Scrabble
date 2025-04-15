package com.jamescookie.scrabble;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Operator {
    public static final Operator EQUALS = new Operator("Equal To", 1);
    public static final Operator LESS_THAN = new Operator("Less Than", 2);
    public static final Operator GREATER_THAN = new Operator("Greater Than", 3);

    private final String code;
    private final int value;

    private Operator(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String toString() {
        return code;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator operator)) return false;

        return Objects.equals(code, operator.code);
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
