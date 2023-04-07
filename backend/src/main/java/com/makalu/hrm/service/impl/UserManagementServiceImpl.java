package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.EmployeeConverter;
import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.EmployeeStatus;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;
import com.makalu.hrm.repository.EmployeeRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.EmployeeService;
import com.makalu.hrm.service.MailService;
import com.makalu.hrm.service.MailTemplateParser;
import com.makalu.hrm.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service

public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final MailService mailService;

    private final MailTemplateParser mailTemplateParser;

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> list() {
        return userConverter.convertToDtoList(userRepository.findAll());
    }

    @Override
    public RestResponseDto getResponseById(UUID employeeId) {
        PersistentUserEntity userEntity = userRepository.findById(employeeId).orElse(null);
        if (userEntity == null) {
            return RestResponseDto.INSTANCE().notFound().message("Employee not found");
        }

        return RestResponseDto.INSTANCE()
                .success()
                .detail(userConverter.convertToDto(userEntity));
    }

    @Override
    @Transactional
    public RestResponseDto changePassword(UserDTO userDTO) {
        try {
            String fullName = null;
            PersistentUserEntity userEntity = userRepository.findById(userDTO.getId()).orElse(null);
            if (userEntity == null) {
                return RestResponseDto.INSTANCE().notFound().message("Invalid User");
            }
            PersistentEmployeeEntity employeeEntity = employeeRepository.findEmployeeByUser(userDTO.getId());
            if (employeeEntity != null) {
                fullName = employeeEntity.getFullname();
            }
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            Map<String, Object> mailMap = Map.of("user", fullName, "email", userEntity.getUsername(), "password", userEntity.getPassword());

            RestResponseDto rdto = mailService.sendHtmMail(userEntity.getUsername(), "Password Reset!!!", mailTemplateParser.sendResetPasswordTemplate(mailMap));

            if (rdto.getStatus() != 200) {
                return RestResponseDto.INSTANCE().internalServerError();
            }
            return RestResponseDto.INSTANCE().success().detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity))).message("User Password Updated");
        } catch (Exception e) {
            log.error("Error resetting password", e);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    @Transactional
    public RestResponseDto changeUserType(UserDTO userDTO) {
        try{
            PersistentUserEntity userEntity = userRepository.findById(userDTO.getId()).orElse(null);
            if(userEntity == null){
                return RestResponseDto.INSTANCE().notFound();
            }
            userEntity.setUserType(userDTO.getUserType());
            return RestResponseDto
                    .INSTANCE()
                    .success().detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity))).message("User Type Updated");
        }catch (Exception e){
            log.error("Error changing user type.",e);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    @Transactional
    public RestResponseDto disableUser(@NotNull UUID userId) {
        try {
            PersistentUserEntity userEntity = userRepository.findById(userId).orElse(null);
            PersistentEmployeeEntity employee = employeeRepository.findEmployeeByUser(userId);
            if (userEntity == null) {
                return RestResponseDto.INSTANCE().notFound();
            }
            userEntity.setEnabled(userEntity.isEnabled()? false: true);
            if(employee != null){
                employee.setEmployeeStatus(userEntity.isEnabled() ? EmployeeStatus.ACTIVE:EmployeeStatus.INACTIVE);

                PersistentEmployeeEntity savedEmployee = employeeRepository.saveAndFlush(employee);

                if(savedEmployee == null){
                    return RestResponseDto.INSTANCE().internalServerError();
                }

            }

            return RestResponseDto.INSTANCE().success().detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity))).message("User Status Updated");
        }catch (Exception e){
            log.error("Error changing user status",e);
           return RestResponseDto.INSTANCE().internalServerError();
        }
    }
}
