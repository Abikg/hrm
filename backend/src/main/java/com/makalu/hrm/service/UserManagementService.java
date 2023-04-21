package com.makalu.hrm.service;

import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface UserManagementService {

    List<UserDTO> list();

    RestResponseDto getResponseById(@NotNull UUID userId);

    RestResponseDto changePassword(UserDTO userDTO);

    RestResponseDto changeUserType(UserDTO userDTO);

    RestResponseDto disableUser(@NotNull UUID userId);

}
