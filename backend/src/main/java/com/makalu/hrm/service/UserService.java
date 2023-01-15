package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.model.RestResponseDto;
import javax.validation.constraints.NotNull;

public interface UserService {

    PersistentUserEntity getCurrentUserEntity();

    RestResponseDto createEmployeeUser(@NotNull String email);

}

