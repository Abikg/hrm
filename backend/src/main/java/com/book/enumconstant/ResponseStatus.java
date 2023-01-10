package com.book.enumconstant;

public enum ResponseStatus {

    SUCCESS("Success", "S00"),

    FAILURE("Failure", "F00"),

    INTERNAL_SERVER_ERROR("Internal Server Error", "F02"),

    INVALID_SESSION("Session Invalid", "F03"),

    BAD_REQUEST("Bad Request", "F04"),

    VALIDATION_FAILED("Validation Fail", "F06"),

    UNAUTHORIZED_USER("Un-Authorized User", "F05"),

    AMBIGUOUS("Ambiguous", "A00"),

    PENDING("Pending", "P00"),

    NOTFOUND("not found", "N00"),

    ACCESS_DENIED("Access Denied", "A00");

    private ResponseStatus() {

    }

    private String key;

    private String value;

    private ResponseStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static ResponseStatus getEnumByValue(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (ResponseStatus v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }

    public static ResponseStatus getEnumByKey(String key) {
        if (key == null)
            throw new IllegalArgumentException();
        for (ResponseStatus v : values())
            if (key.equalsIgnoreCase(v.getKey()))
                return v;
        throw new IllegalArgumentException();
    }

}
