package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.DepartmentConverter;
import com.makalu.hrm.converter.EmployeeConverter;
import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.DepartmentRepository;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.DepartmentService;
import com.makalu.hrm.service.EmployeeService;
import com.makalu.hrm.validation.DepartmentValidation;
import com.makalu.hrm.validation.error.DepartmentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentConverter departmentConverter;

    private final DepartmentValidation departmentValidation;

    private final EmployeeRepository employeeRepository;

    private final EmployeeConverter employeeConverter;

    private final UserRepository userRepository;


    @Override
    public List<DepartmentDTO> list() {
        return departmentConverter.convertToDtoList(departmentRepository.findAll());
    }

    @Override
    @Transactional
    public RestResponseDto save(@NotNull DepartmentDTO departmentDTO) {
        try {
            DepartmentError error = departmentValidation.validateOnSave(departmentDTO);

            if (!error.isValid()) {
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", departmentDTO));
            }

            return RestResponseDto
                    .INSTANCE()
                    .success()
                    .detail(departmentConverter.convertToDto(
                            departmentRepository.saveAndFlush(departmentConverter.convertToEntity(departmentDTO))
                    ));
        } catch (Exception ex) {
            log.error("Error while creating department", ex);
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError()
                    .detail(departmentDTO);
        }
    }

    @Override
    public RestResponseDto getResponseById(@NotNull UUID departmentId) {
        PersistentDepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElse(null);
        if (departmentEntity == null) {
            return RestResponseDto.INSTANCE().notFound().message("Department not found");
        }
        DepartmentDTO dto = departmentConverter.convertToDto(departmentEntity);
        RestResponseDto  rdto = this.getEmployeeList();
        return RestResponseDto.INSTANCE().success().detail(dto);
    }

    @Override
    @Transactional
    public RestResponseDto update(@NotNull DepartmentDTO departmentDTO) {
        try {
            DepartmentError error = departmentValidation.validateOnUpdate(departmentDTO);

           if (!error.isValid()) {
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", departmentDTO));
            }

            PersistentDepartmentEntity departmentEntity = departmentRepository.findById(departmentDTO.getId()).orElse(null);

            if (departmentEntity == null) {
                return RestResponseDto.INSTANCE().validationError().message("Department not found").detail(departmentDTO);
            }
            if(departmentDTO.getManagerId() != null) {
                if (departmentEntity.getManager() != null){
                    if (!departmentDTO.getManagerId().equals(departmentEntity.getManager().getId())) {
                        RestResponseDto removeExistingManager = this.removeExistingManager(departmentEntity.getManager().getEmail());
                        if (removeExistingManager.getStatus() != 200) {
                            return RestResponseDto.INSTANCE().internalServerError().message("Error creating manager").detail(departmentDTO);
                        }
                    }
                }
                RestResponseDto setNewManager = this.departmentSetManager(employeeRepository.findById(departmentDTO.getManagerId()).orElse(null));
                if (setNewManager.getStatus() != 200) {
                    return RestResponseDto.INSTANCE().internalServerError().message("Error creating manager").detail(departmentDTO);
                }
                departmentEntity.setManager((PersistentEmployeeEntity) setNewManager.getDetail());
            }
            return RestResponseDto
                    .INSTANCE()
                    .success()
                    .detail(departmentConverter.convertToDto(
                            departmentRepository.saveAndFlush(departmentEntity)
                    ));
        } catch (Exception ex) {
            log.error("Error while creating department", ex);
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError()
                    .detail(departmentDTO);
        }
    }

    @Override
    @Transactional
    public RestResponseDto delete(UUID departmentId) {
        try {
            PersistentDepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElse(null);

            if (departmentEntity == null) {
                return RestResponseDto.INSTANCE().notFound().message("Department not found");
            }
            departmentRepository.delete(departmentEntity);
            return RestResponseDto.INSTANCE().success();
        } catch (Exception ex) {
            log.error("Error while creating department", ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Transactional
    @Override
    public RestResponseDto departmentSetManager(PersistentEmployeeEntity employee) {
        try{
            PersistentUserEntity userEntity = userRepository.findByUsername(employee.getEmail()).orElse(null);

            userEntity.setUserType(UserType.MANAGER);
            userRepository.saveAndFlush(userEntity);
            return RestResponseDto.INSTANCE().success().detail(employee);
        }catch (Exception e){
            log.error("Error creating manager: ",e);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    @Transactional
    public RestResponseDto removeExistingManager(String email) {
        try{
            PersistentUserEntity userEntity = userRepository.findByUsername(email).orElse(null);
            userEntity.setUserType(UserType.EMPLOYEE);
            userRepository.saveAndFlush(userEntity);
            return RestResponseDto.INSTANCE().success();
        }catch (Exception e){
            log.error("Error creating manager: ",e);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    public RestResponseDto getEmployeeList() {
        try {
            List<EmployeeDTO> employeeDTOList = employeeConverter.convertToDtoList(employeeRepository.findAllByActiveEmployees());
            return RestResponseDto.INSTANCE().success().detail(employeeDTOList);
        }catch (Exception e){
            return RestResponseDto.INSTANCE().notFound();
        }
    }

}
