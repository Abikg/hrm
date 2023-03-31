package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.EmployeeConverter;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {
    private final UserRepository userRepository;
    
    private final EmployeeRepository employeeRepository;
    
    private final EmployeeConverter employeeConverter;
    
    @Transactional
    @Override
    public RestResponseDto convertToManager(PersistentEmployeeEntity employee) {
        try{
            PersistentUserEntity userEntity = userRepository.findByUsername(employee.getEmail()).orElse(null);
            if(!userEntity.getUserType().equals(UserType.MANAGER)) {
                userEntity.setUserType(UserType.MANAGER);
                userRepository.saveAndFlush(userEntity);
            }
            return RestResponseDto.INSTANCE().success().detail(employee);
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
