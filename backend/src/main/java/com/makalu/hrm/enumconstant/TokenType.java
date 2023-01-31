package com.makalu.hrm.enumconstant;

public enum TokenType {

    EMAIL_VERIFICATION("EmailVerification"), RESET_PASSWORD("ResetPassword");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }


    public static TokenType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (TokenType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
