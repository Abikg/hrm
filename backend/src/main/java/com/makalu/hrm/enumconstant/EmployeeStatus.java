package com.makalu.hrm.enumconstant;


public enum EmployeeStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    RESIGNED("resigned");

    private final String value;

    EmployeeStatus(String value) {
        this.value = value;
    }

    public static EmployeeStatus getEnum(String val) {
        for (EmployeeStatus status : values()) {
            if (status.value.equals(val)) {
                return status;
            }
        }

        return null;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }


}
