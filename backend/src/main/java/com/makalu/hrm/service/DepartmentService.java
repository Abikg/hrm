package com.makalu.hrm.service;

import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.PositionDTO;
import com.makalu.hrm.model.RestResponseDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    List<DepartmentDTO> list();

    RestResponseDto save(DepartmentDTO departmentDTO);

    RestResponseDto getResponseById(@NotNull UUID departmentId);

    RestResponseDto update(DepartmentDTO departmentDTO);

    RestResponseDto delete(UUID departmentId);

    RestResponseDto getEmployeeListByDepartment(UUID departmentId);

    RestResponseDto employeeSetDepartmentManager(@NotNull UUID employeeId,UUID departmentId);
}
