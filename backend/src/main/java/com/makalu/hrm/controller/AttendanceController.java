package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @GetMapping("/punchIn")
    public String punchIn(HttpServletRequest request) {
        try {
            attendanceService.punchIn(IPUtils.getClientIp(request));
        } catch (Exception ex) {
            log.error("Error while punchIn", ex);
        }
        return "redirect:/";
    }

    @GetMapping("/punchOut")
    public String punchOut(HttpServletRequest request) {
        try {
            attendanceService.punchOut(IPUtils.getClientIp(request));
        } catch (Exception ex) {
            log.error("Error while punchOut", ex);
        }
        return "redirect:/";
    }

    @GetMapping("/userList")
    public String userList(AttendanceDto attendanceDto, ModelMap map) {

        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.USER_LIST, userService.findALl());
            map.addAttribute(ParameterConstant.ADMIN_FLAG, true);
            return "attendance/attendance_main";
        } else {
            map.addAttribute(ParameterConstant.ADMIN_FLAG, false);
            return "attendance/attendance_main";
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/allUserAttendance")
    public String getAllUserAttendance(AttendanceDto attendanceDto, ModelMap map) {
        map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findAllUserAttendance(attendanceDto.getPage()));
        return "attendance/allUsersAttendance";

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/allUserAttendanceByDate")
    public String getAllUserAttendanceByDate(AttendanceDto attendanceDto, ModelMap map) {
        map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findAllUserAttendanceFilteredByDate(attendanceDto));
        return "attendance/allUsersAttendance";

    }

    @GetMapping("/attendancelist")
    public String loginedUserAttendanceList(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findByUser_Id(attendanceDto.getId(), attendanceDto.getPage()));
        } else
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findByUser_Id(AuthenticationUtils.getCurrentUser().getUserId(), attendanceDto.getPage()));
        return "attendance/UserAttendancelist";
    }

    @GetMapping("/dateFilterForUser")
    public String filterByDate(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(attendanceDto.getId(), attendanceDto));
        } else
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(AuthenticationUtils.getCurrentUser().getUserId(), attendanceDto));
        return "attendance/UserAttendancelist";
    }

}
