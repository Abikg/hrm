package com.makalu.hrm.enumconstant;

public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DELETED("deleted");

    private final String value;

    Status(String value){
        this.value = value;
    }

    public static Status getEnum(String val){
        for (Status status : values()){
            if (status.value.equals(val)){
                return status;
            }
        }

        return null;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
