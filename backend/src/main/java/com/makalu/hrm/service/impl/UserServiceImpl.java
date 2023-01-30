package com.makalu.hrm.service.impl;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.MailService;
import com.makalu.hrm.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final PasswordUtil passwordUtil = new PasswordUtil();

    @Override
    @Transactional
    public RestResponseDto createEmployeeUser(String email) {
        try {
            PersistentUserEntity userEntity = new PersistentUserEntity();
            int len = 10;
            String randomPassword = passwordUtil.generatePassword(len);
            userEntity.setUsername(email);
            userEntity.setPassword(passwordEncoder.encode(randomPassword));
            userEntity.setEnabled(true);
            userEntity.setAccountExpired(false);
            userEntity.setAccountLocked(false);
            userEntity.setPasswordExpired(false);
            userEntity.setUserType(UserType.EMPLOYEE);

            RestResponseDto rdto = mailService.sendMail(userEntity.getUsername(), "HRM Registeration", randomPassword);

            if (rdto.getStatus() != 200) {
                return RestResponseDto.INSTANCE().internalServerError();
            }
            return RestResponseDto.INSTANCE().success()
                    .detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity)));

        } catch (Exception ex) {
            log.error("Error while creating user", ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    public PersistentUserEntity getCurrentUserEntity() {
        return userRepository.findById(AuthenticationUtils.getCurrentUser().getUserId()).orElse(null);
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
    public List<PersistentUserEntity> findAllByUserType(UserType userType) {
        return null;
    }

    @Override
    public List<PersistentUserEntity> findALl() {
        return null;
    }
}
