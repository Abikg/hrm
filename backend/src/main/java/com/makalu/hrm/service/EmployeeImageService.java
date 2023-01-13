package com.makalu.hrm.service;

import com.makalu.hrm.model.EmployeeImageDTO;
import com.makalu.hrm.model.EmployeeImageDTO;
import com.makalu.hrm.model.RestResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface EmployeeImageService {

    List<EmployeeImageDTO> list();

    RestResponseDto save(@NotNull MultipartFile file);

    RestResponseDto getResponseById(@NotNull UUID employeeImageId);

    RestResponseDto update(EmployeeImageDTO employeeImageDTO);

    RestResponseDto delete(UUID employeeImageId);

}
