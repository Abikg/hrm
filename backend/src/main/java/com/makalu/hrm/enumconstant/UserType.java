package com.makalu.hrm.enumconstant;

public enum UserType {
    SUPER_ADMIN("Super Admin"),
    ADMIN("Admin"),
    EMPLOYEE("Employee");

    private final String value;

    UserType(String value){
        this.value = value;
    }

    public static UserType getEnum(String val){
        for (UserType status : values()){
            if (status.value.equals(val)){
                return status;
            }
        }

        return null;
    }

    public String getValue(){
        return this.value;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
