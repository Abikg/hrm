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
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @GetMapping("/allUserAttendance")
//    public String getAllUserAttendanceNoFilter(AttendanceDto attendanceDto, ModelMap map) {
//        map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
//        return "attendance/allUsersAttendance";
//
//    }

    @GetMapping("/filter")
    public String AttendanceList(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            if(attendanceDto.getId()==null&&attendanceDto.getFromDate()==null&&attendanceDto.getToDate()==null)
            {
                map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
                return "attendance/allUsersAttendance";
            }
            else if(attendanceDto.getId()==null&&attendanceDto.getFromDate()!=null&&attendanceDto.getToDate()!=null){
                map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
                return "attendance/allUsersAttendance";
            }
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
        } else{
            attendanceDto.setId(AuthenticationUtils.getCurrentUser().getUserId());
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
        }
        return "attendance/UserAttendancelist";
    }
}
