package com.book.service;

import com.book.model.DepartmentDTO;
import com.book.model.RestResponseDto;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    List<DepartmentDTO> list();

    RestResponseDto save(DepartmentDTO departmentDTO);

    RestResponseDto getResponseById(@NotNull UUID departmentId);

    RestResponseDto update(DepartmentDTO departmentDTO);

    RestResponseDto delete(UUID departmentId);
}
