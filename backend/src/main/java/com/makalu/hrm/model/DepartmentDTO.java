package com.makalu.hrm.model;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
public class DepartmentDTO {

    private UUID id;

    private String title;

    private String detail;

    private String departmentCode;

    private EmployeeDTO manager;

}
