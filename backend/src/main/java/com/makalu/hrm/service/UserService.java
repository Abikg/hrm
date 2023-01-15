package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.model.RestResponseDto;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface UserService {

    PersistentUserEntity getCurrentUserEntity();

    RestResponseDto createEmployeeUser(@NotNull String email);

    RestResponseDto updateEmployeeUser(String email, UUID userId);
}

