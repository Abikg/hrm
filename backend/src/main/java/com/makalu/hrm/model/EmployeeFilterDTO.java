package com.makalu.hrm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeFilterDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String employeeId;
    private String department;
    private String position;
}
