package com.book.service.impl;

import com.book.converter.EmployeeConverter;
import com.book.model.DepartmentDTO;
import com.book.model.EmployeeDTO;
import com.book.model.PositionDTO;
import com.book.model.RestResponseDto;
import com.book.repository.EmployeeRepository;
import com.book.service.DepartmentService;
import com.book.service.EmployeeService;
import com.book.service.PositionService;
import com.book.validation.EmployeeValidation;
import com.book.validation.error.EmployeeError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidation employeeValidation;

    @Override
    public List<EmployeeDTO> list() {
        return employeeConverter.convertToDtoList(employeeRepository.findAll());
    }


    @Override
    @Transactional
    public RestResponseDto save(@NotNull EmployeeDTO employeeDTO) {
        EmployeeError error = employeeValidation.validateOnSave(employeeDTO);

        if(!error.isValid()){
            return RestResponseDto.INSTANCE()
                    .validationError()
                    .detail(Map.of("error",error,"data",employeeDTO));
        }

        return  null;
    }

    @Override
    public RestResponseDto getResponseById(UUID employeeId) {
        return null;
    }

    @Override
    public RestResponseDto update(EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public RestResponseDto delete(UUID employeeId) {
        return null;
    }

    @Override
    public RestResponseDto getPositionAndDepartmentList() {
        try {
            List<PositionDTO> positionDTOList = positionService.list();
            List<DepartmentDTO> departmentDTOList = departmentService.list();


            return RestResponseDto.INSTANCE()
                    .success().detail(Map.of("positionList",positionDTOList,"departmentList",departmentDTOList));
        }catch (Exception ex){
            log.error("Error while creating department", ex);
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError();

        }
    }
}
