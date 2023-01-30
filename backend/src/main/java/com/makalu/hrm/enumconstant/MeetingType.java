package com.makalu.hrm.enumconstant;

public enum MeetingType {
    BOD("Board Of Director"),
    EMPLOYEE("Employee");

    private final String value;

    MeetingType(String value) {
        this.value = value;
    }

    public static MeetingType getEnum(String val) {
        for (MeetingType status : values()) {
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
