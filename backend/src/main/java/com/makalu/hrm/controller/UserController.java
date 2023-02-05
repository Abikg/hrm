package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/userList")
    public String userList(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.USER_LIST, userService.findALl());
            return "attendance/attendance_main";
        } else {
            return "attendance/attendance_main";
        }
    }
}
