package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.UserDTO;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private  final UserConverter userConverter;
    @Override
    public PersistentUserEntity getCurrentUserEntity() {
        return userRepository.findById(AuthenticationUtils.getCurrentUser().getUserId()).orElse(null);
    }
    @Override
    public List<PersistentUserEntity> findAllByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
    @Override
    public List<UserDTO> findALl(){
        return userConverter.convertToDtoList(userRepository.findAll());

    }

    @Override
    public UserDTO findById(UUID userid) {
        return userConverter.convertToDto(userRepository.findById(userid).get());
    }
}
