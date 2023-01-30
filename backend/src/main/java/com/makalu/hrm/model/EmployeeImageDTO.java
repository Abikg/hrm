package com.makalu.hrm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeImageDTO {

    private UUID id;

    private String name;

    private String type;

    private byte[] image;
}
