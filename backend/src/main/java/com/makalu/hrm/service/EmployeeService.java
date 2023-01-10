package com.makalu.hrm.service;

import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<EmployeeDTO> list();

    RestResponseDto save(EmployeeDTO employeeDTO);

    RestResponseDto getResponseById(@NotNull UUID employeeId);

    RestResponseDto update(EmployeeDTO employeeDTO);

    RestResponseDto delete(UUID employeeId);

    RestResponseDto getPositionAndDepartmentList();
}
