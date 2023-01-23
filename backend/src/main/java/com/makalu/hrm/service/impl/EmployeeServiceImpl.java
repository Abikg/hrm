package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.EmployeeConverter;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.enumconstant.EmployeeStatus;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.*;
import com.makalu.hrm.repository.DepartmentRepository;
import com.makalu.hrm.repository.EmployeeImageRepository;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.repository.PositionRepository;
import com.makalu.hrm.service.*;
import com.makalu.hrm.validation.EmployeeValidation;
import com.makalu.hrm.validation.error.EmployeeError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidation employeeValidation;
    private final EmployeeImageService employeeImageService;
    private final UserService userService;

    @Override
    public List<EmployeeDTO> list() {
            return employeeConverter.convertToDtoList(employeeRepository.findAll());
    }


    @Override
    @Transactional
    public RestResponseDto save(@NotNull EmployeeDTO employeeDTO) {
        try {
                long entityCount;

                EmployeeError error = employeeValidation.validateOnSave(employeeDTO);

            if (!error.isValid()) {
                return RestResponseDto.INSTANCE()
                        .validationError()
                        .detail(Map.of("error", error, "data", employeeDTO));
            }
            if (employeeDTO.getEmpImage() != null && !employeeDTO.getEmpImage().isEmpty()) {
                RestResponseDto imageResponseDto = employeeImageService.save(employeeDTO.getEmpImage());

                if (imageResponseDto.getStatus() != 200) {
                    error.setEmpImage("Image upload failed. Please try again");
                    return RestResponseDto.INSTANCE()
                            .internalServerError()
                            .detail(Map.of("error", error, "data", employeeDTO));
                }else{
                    EmployeeImageDTO employeeImageDTO = (EmployeeImageDTO) imageResponseDto.getDetail();
                    employeeDTO.setEmployeeImageId(employeeImageDTO.getId());
                }
            }
            if(employeeDTO.isCreateUser()) {
                RestResponseDto userResponseDto = userService.createEmployeeUser(employeeDTO.getEmail());

                if (userResponseDto.getStatus() == 200) {
                    UserDTO userDTO = (UserDTO) userResponseDto.getDetail();
                    employeeDTO.setUserId(userDTO.getId());
                }
            }
            entityCount = employeeRepository.count();
            employeeDTO.setEntityEmployeeId(entityCount+1);
            employeeDTO.setEmployeeStatus(EmployeeStatus.ACTIVE);
            return RestResponseDto.INSTANCE()
                    .success().detail(employeeConverter.convertToDto(
                            employeeRepository.saveAndFlush(employeeConverter.convertToEntity(employeeDTO))));


        }catch (Exception ex){
            log.error("Error while creating employee",ex);
            return  RestResponseDto.INSTANCE()
                    .internalServerError()
                    .detail(employeeDTO);
        }
    }

    @Override
    public RestResponseDto getResponseById(UUID employeeId) {
        PersistentEmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        if(employeeEntity == null){
            return RestResponseDto.INSTANCE().notFound().message("Employee not found");
        }

        return RestResponseDto.INSTANCE()
                .success()
                .detail(employeeConverter.convertToDto(employeeEntity));
    }

    @Override
    @Transactional
    public RestResponseDto update(EmployeeDTO employeeDTO) {
        try{
            EmployeeError error = employeeValidation.validateOnUpdate(employeeDTO);
            if(!error.isValid()){
                return RestResponseDto.INSTANCE().
                        validationError().
                        detail(Map.of("error",error,"data",employeeDTO));
            }
            PersistentEmployeeEntity employeeEntity = employeeRepository.findById(employeeDTO.getId()).orElse(null);
            if(employeeEntity == null){
                return RestResponseDto.INSTANCE().notFound().message("Employee not found");
            }
            if(employeeEntity.getUser() != null) {
                employeeDTO.setUserId(employeeEntity.getUser().getId());
            }
            if(employeeEntity.getImage() != null){
                employeeDTO.setEmployeeImageId(employeeEntity.getImage().getId());
            }
            if(!employeeEntity.getEmail().equals(employeeDTO.getEmail())){
                //employee update garda email update garna mildaina hai so user ni update garna bhayana
                 //userService.updateEmployeeUser(employeeDTO.getEmail(), employeeDTO.getUserId());
            }
            if(!employeeDTO.getEmpImage().isEmpty()){
                if (employeeEntity.getImage() != null) {
                    employeeImageService.update(employeeDTO.getEmpImage(), employeeDTO.getEmployeeImageId());
                }else {
                    RestResponseDto imageResponse = employeeImageService.save(employeeDTO.getEmpImage());
                    EmployeeImageDTO employeeImageDTO = (EmployeeImageDTO) imageResponse.getDetail();
                    employeeDTO.setEmployeeImageId(employeeImageDTO.getId());
                }
            }
            return RestResponseDto.INSTANCE()
                    .success().detail(employeeConverter.convertToDto(employeeRepository.saveAndFlush(
                            employeeConverter.copyConvertToEntity(employeeDTO,employeeEntity))));
        }catch (Exception e){
            log.error("Error updating employee",e);
            return RestResponseDto.INSTANCE().internalServerError().detail(employeeDTO);
        }
    }


}
