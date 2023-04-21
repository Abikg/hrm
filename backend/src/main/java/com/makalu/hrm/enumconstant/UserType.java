package com.makalu.hrm.enumconstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UserType {
    SUPER_ADMIN("Super Admin"),
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public static UserType getEnum(String val) {
        for (UserType status : values()) {
            if (status.value.equals(val)) {
                return status;
            }
        }

        return null;
    }

    public String getValue() {
        return this.value;
    }
    public String getName(){
        return this.name();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static List<Map<String,String>> getAllUserType(){
        List values = new ArrayList<>();
        for(UserType userType : UserType.values()){
            values.add(Map.of(userType.getName(), userType.getValue()));
        }

        return values;
    }
}
