package com.makalu.hrm.model;

import com.makalu.hrm.enumconstant.UserType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;


@Data
public class UserDTO {

    private UUID id;

    private String username;

    private String password;

    boolean enabled = true;

    boolean accountExpired = false;

    boolean accountLocked = false;

    boolean passwordExpired = false;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String fullName;

}
