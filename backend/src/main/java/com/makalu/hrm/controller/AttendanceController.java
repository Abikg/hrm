package com.makalu.hrm.controller;

import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

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
    @PostMapping("/punchOut")
    public RestResponseDto punchOut(AttendanceDto attendanceDto, HttpServletRequest request) {
        RestResponseDto restResponseDto = new RestResponseDto();
        try {
            restResponseDto = attendanceService.punchOut(attendanceDto.getTime(), IPUtils.getClientIp(request));
        } catch (Exception ex) {
            log.error("Error while punchOut", ex);
        }
        return restResponseDto;
    }

    @ResponseBody
    @GetMapping("/filter")
    public RestResponseDto AttendanceList(AttendanceDto attendanceDto) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            return attendanceService.filter(attendanceDto);
        } else {
            attendanceDto.setId(AuthenticationUtils.getCurrentUser().getUserId());
            return attendanceService.filter(attendanceDto);
        }
    }
}

