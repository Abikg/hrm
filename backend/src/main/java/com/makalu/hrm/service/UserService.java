
package com.makalu.hrm.service;


import com.makalu.hrm.model.EmployeeDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.model.UserDTO;

import javax.validation.constraints.NotNull;

public interface UserService {

    RestResponseDto createEmployeeUser(@NotNull String email);
}

