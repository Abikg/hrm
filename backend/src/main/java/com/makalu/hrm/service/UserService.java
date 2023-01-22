package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    PersistentUserEntity getCurrentUserEntity();
    List<PersistentUserEntity> findAllByUserType(UserType userType);

    List<UserDTO> findALl();
    UserDTO findById(UUID userid);
}
