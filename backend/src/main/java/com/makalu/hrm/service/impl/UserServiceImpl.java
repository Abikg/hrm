package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RestResponseDto createEmployeeUser(String email) {
        try {
            PersistentUserEntity userEntity = new PersistentUserEntity();
            userEntity.setUsername(passwordEncoder.encode("test12345"));
            userEntity.setUserType(UserType.EMPLOYEE);

            return RestResponseDto.INSTANCE().success()
                    .detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity)));
        }
        catch (Exception ex) {
            log.error("Error while creating user",ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }
}