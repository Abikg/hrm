package com.makalu.hrm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeSearchDTO {
    private List<EmployeeDTO> employees;
    private long total;

}
