package com.makalu.hrm.service;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;

import java.util.List;

public interface UserService {

    PersistentUserEntity getCurrentUserEntity();
    List<PersistentUserEntity> findAllByUserType(UserType userType);

    List<PersistentUserEntity> findALl();
}
