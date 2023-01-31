package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.UserDTO;

import com.makalu.hrm.model.RestResponseDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface UserService {

    PersistentUserEntity getCurrentUserEntity();

    RestResponseDto createEmployeeUser(@NotNull String email, @NotNull String name);

    RestResponseDto getResponseById(@NotNull UUID userId);

    List<PersistentUserEntity> findAllByUserType(UserType userType);

    List<UserDTO> findALl();
    UserDTO findById(UUID userid);
}
