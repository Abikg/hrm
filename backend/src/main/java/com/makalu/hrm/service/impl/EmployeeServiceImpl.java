package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.EmployeeConverter;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.*;
import com.makalu.hrm.repository.EmployeeImageRepository;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.service.*;
import com.makalu.hrm.validation.EmployeeValidation;
import com.makalu.hrm.validation.error.EmployeeError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final EmployeeImageService employeeImageService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeDTO> list() {
        return employeeConverter.convertToDtoList(employeeRepository.findAll());
    }


    @Override
    @Transactional
    public RestResponseDto save(@NotNull EmployeeDTO employeeDTO) {
        try {
            EmployeeError error = employeeValidation.validateOnSave(employeeDTO);

            if (!error.isValid()) {
                return RestResponseDto.INSTANCE()
                        .validationError()
                        .detail(Map.of("error", error, "data", employeeDTO));
            }
            if (employeeDTO.getEmpImage() != null && !employeeDTO.getEmpImage().isEmpty()) {
                RestResponseDto imageResponseDto = employeeImageService.save(employeeDTO.getEmpImage());

                if (imageResponseDto.getStatus() == 500) {
                    error.setEmpImage("Image upload failed. Please try again");
                    return RestResponseDto.INSTANCE()
                            .internalServerError()
                            .detail(Map.of("error", error, "data", employeeDTO));
                }else if(imageResponseDto.getStatus() == 200){
                    EmployeeImageDTO employeeImageDTO = (EmployeeImageDTO) imageResponseDto.getDetail();
                    employeeDTO.setEmployeeImageId(employeeImageDTO.getId());
                }
            }

            RestResponseDto userResponseDto = userService.createEmployeeUser(employeeDTO.getEmail());

            if(userResponseDto.getStatus() == 200){
                UserDTO userDTO = (UserDTO) userResponseDto.getDetail();
                employeeDTO.setUserId(userDTO.getId());
                return RestResponseDto.INSTANCE()
                        .success().detail(employeeConverter.convertToDto(
                                employeeRepository.saveAndFlush(employeeConverter.convertToEntity(employeeDTO))));
            }

            return  RestResponseDto.INSTANCE()
                    .internalServerError()
                    .detail(Map.of("error","Error occurred while creating employee. Please try again later" , "data", employeeDTO));

        }catch (Exception ex){
            log.error("Error while creating employee",ex);
            return  RestResponseDto.INSTANCE()
                    .internalServerError()
                    .detail(employeeDTO);
        }
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
