package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.model.RestResponseDto;

public interface ManagerService {
    RestResponseDto getEmployeeList();

    RestResponseDto convertToManager(PersistentEmployeeEntity employee);
}
