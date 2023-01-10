package com.makalu.hrm.validation.error;

import lombok.Data;

@Data
public class EmployeeError {

    private boolean valid;

    private String fullname;

    private String address;

    private String phone;

    private String email;

    private String empImage;

    private String position;

    private String department;

}
