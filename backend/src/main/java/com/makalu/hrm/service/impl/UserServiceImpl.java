package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.MailDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.MailService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
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
            String randomPassword =  passwordUtil.generatePassword(len);
            userEntity.setUsername(email);
            userEntity.setPassword(passwordEncoder.encode(randomPassword));
            userEntity.setEnabled(true);
            userEntity.setAccountExpired(false);
            userEntity.setAccountLocked(false);
            userEntity.setPasswordExpired(false);
            userEntity.setUserType(UserType.EMPLOYEE);

            RestResponseDto rdto = mailService.sendMail(userEntity.getUsername(),"HRM Registeration",randomPassword);

            if(rdto.getStatus() !=200){
               return RestResponseDto.INSTANCE().internalServerError();
            }
            return RestResponseDto.INSTANCE().success()
                    .detail(userConverter.convertToDto(userRepository.saveAndFlush(userEntity)));

        }
        catch (Exception ex) {
            log.error("Error while creating user",ex);
            return RestResponseDto.INSTANCE().internalServerError();
        }
    }

    @Override
    @Transactional
    public RestResponseDto updateEmployeeUser(String email, UUID userId) {
        try{
            PersistentUserEntity user = userRepository.findById(userId).orElse(null);
            if(user == null){
                return RestResponseDto.INSTANCE().notFound().message("User not found");
            }
            user.setUsername(email);
            return RestResponseDto.INSTANCE()
                    .success().detail(userConverter.convertToDto(userRepository.saveAndFlush(user)));
        }catch (Exception ex){
            log.error("Error while updating user",ex);
            return  RestResponseDto.INSTANCE().internalServerError();
        }

    }
}