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

    private String contactAddress;

    private String contactPhone;

    private String contactEmail;

    private String dob;

    private String gender;

    private String maritalStatus;

    private String previousCompany;

    private String jobTitle;

    private String joinDate;

    private String leftDate;

    private String jobDesc;

}
