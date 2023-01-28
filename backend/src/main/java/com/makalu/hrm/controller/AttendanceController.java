package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;


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

    @ResponseBody
    @GetMapping("/punchOut")
    public RestResponseDto punchOut(HttpServletRequest request) {
        RestResponseDto restResponseDto =new RestResponseDto();
        try {
             restResponseDto= attendanceService.punchOut(IPUtils.getClientIp(request));
        } catch (Exception ex) {
            log.error("Error while punchOut", ex);
        }
        return restResponseDto;
    }

    @ResponseBody
    @PostMapping("/nextDayPunchout")
    public  RestResponseDto nextDayPunchout(@RequestParam String time,HttpServletRequest request){
        RestResponseDto restResponseDto=new RestResponseDto();
        try {
            restResponseDto=attendanceService.setPunchinAnotherDay(time, IPUtils.getClientIp(request));
        }catch (Exception ex)
        {
            log.error("Error while punchout",ex);
        }
        return restResponseDto;

    }
    @GetMapping("/userList")
    public String userList(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.USER_LIST, userService.findALl());

            return "attendance/attendance_main";
        } else {
            return "attendance/attendance_main";
        }
    }
    @GetMapping("/filter")
    public String AttendanceList(AttendanceDto attendanceDto, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
        } else{
            attendanceDto.setId(AuthenticationUtils.getCurrentUser().getUserId());
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.Filter(attendanceDto));
        }
        return "attendance/UserAttendancelist";
    }
}
