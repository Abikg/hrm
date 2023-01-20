package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.service.impl.AttendanceServiceImp;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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

    @GetMapping("/punchOut")
    public String punchOut(HttpServletRequest request) {
        try {
            attendanceService.punchOut(IPUtils.getClientIp(request));
        } catch (Exception ex) {
            log.error("Error while punchOut", ex);
        }
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(ModelMap modelMap) {
        modelMap.put(ParameterConstant.ATTENDANCE_LIST, attendanceService.findAll());
        return "attendance/list";
    }

    @GetMapping("/logineduser/attendancelist")
    public String loginedUserAttendanceList(ModelMap map) {
        map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findAllByUser(AuthenticationUtils.getCurrentUser().getUserId()));
        return "mypunchin/list";
    }

    @PostMapping("/filter")
    public String filterByDate(AttendanceDto attendanceDto, ModelMap map) {
        map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(attendanceDto));
        return "mypunchin/list";
    }


}
