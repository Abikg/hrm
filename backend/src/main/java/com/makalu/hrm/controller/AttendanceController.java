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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/userList")
    public String userList(ModelMap map) {
        map.addAttribute(ParameterConstant.USER_LIST, userService.findALl());
        return "attendance/userList";
    }
    @GetMapping("/attendancelist")
    public String loginedUserAttendanceList(AttendanceDto attendanceDto, ModelMap map) {
        if (attendanceDto.getId() == null)
            attendanceDto.setId(AuthenticationUtils.getCurrentUser().getUserId());
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findByUser_Id(attendanceDto.getId(), attendanceDto.getPage()));
        } else
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.findByUser_Id(attendanceDto.getId(), attendanceDto.getPage()));
        return "attendance/list";
    }
    @GetMapping("/dateFilter")
    public String filterByDate(AttendanceDto attendanceDto, @RequestParam int page, ModelMap map) {
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(attendanceDto.getId(), attendanceDto, page));
        } else
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(AuthenticationUtils.getCurrentUser().getUserId(), attendanceDto, page));
        return "attendance/list";
    }
    @GetMapping("/dateFilter2")//filter 2  with RequestParam with StringDate paramater  was necessary for pagination
    public String filterByDateInString(AttendanceDto attendanceDto, @RequestParam String fromDateString, String toDateString, ModelMap map) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
        attendanceDto.setFromDate(dateFormat.parse(fromDateString));
        attendanceDto.setToDate(dateFormat.parse(toDateString));
        if (AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(attendanceDto.getId(), attendanceDto, attendanceDto.getPage()));

        } else {
            map.addAttribute(ParameterConstant.RESPONSE, attendanceService.filterByDate(AuthenticationUtils.getCurrentUser().getUserId(), attendanceDto, attendanceDto.getPage()));
        }

        return "attendance/list";
    }
}
