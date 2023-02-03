package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.exceptions.DataNotFoundException;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.MeetingMinuteService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@Slf4j
@RequestMapping("/meetingMinutes")
@Controller
@RequiredArgsConstructor
public class MeetingMinuteController {
    private final MeetingMinuteService meetingMinuteMinuteService;
    private final UserService userService;
    private final FieldService fieldService;

    @GetMapping("/employees/list")
    public String showEmployeesMeeting(ModelMap map) {
        map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), MeetingType.EMPLOYEE.name().toUpperCase());
        return "meetingMinute/list";
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/bod/list")
    public String List(ModelMap map) {
        map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), MeetingType.BOD.name().toUpperCase());
        return "meetingMinute/list";
    }

    @ResponseBody
    @GetMapping("/allMinutes")
    public ResponseEntity<RestResponseDto> showEmployeeMeetig(@RequestParam String meetingType) {
        if (meetingType.toUpperCase().equals(MeetingType.BOD.name().toUpperCase()) && AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(meetingMinuteMinuteService.findAll(MeetingType.BOD)).column(fieldService.getMinuteFields()));
        } else {
            return ResponseEntity.ok(RestResponseDto.INSTANCE().success().detail(meetingMinuteMinuteService.findAll(MeetingType.EMPLOYEE)).column(fieldService.getMinuteFields()));
        }
    }
    @GetMapping("/employeeForm/{meetingtype}")
    public String showform(@PathVariable MeetingType meetingtype, ModelMap map) {
        if (MeetingType.EMPLOYEE.name().equals(meetingtype.name())) {
            map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), meetingtype.name().toUpperCase());
            map.addAttribute(ParameterConstant.ATTENDEDBY, userService.findALl());
            map.addAttribute(ParameterConstant.MEETIINGTYPEURL, "/meetingMinutes/employee/save");
            return "meetingMinute/form";
        } else if (MeetingType.BOD.name().equals(meetingtype.name())
                && AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), meetingtype.name().toUpperCase());
            map.addAttribute(ParameterConstant.ATTENDEDBY, userService.findAllByUserType(UserType.SUPER_ADMIN));
            map.addAttribute(ParameterConstant.MEETIINGTYPEURL, "/meetingMinutes/bod/save");
            return "meetingMinute/form";
        } else {
            return "error/unauthorized";
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/bod/save")
    public String saveBod(MeetingMinutesDto meetingDto, ModelMap map) {
        try {
            RestResponseDto restResponseDto = meetingMinuteMinuteService.save(meetingDto);
            UUID id = ((MeetingMinutesDto) restResponseDto.getDetail()).getId();
            return "redirect:/meetingMinutes/show/" + id;
        } catch (Exception e) {
            log.error("error creating minute", e);
            map.addAttribute(ParameterConstant.ERROR, "error creating minute");
        }
        return "meetingMinute/form";
    }
    @PostMapping("/employee/save")
    public String saveEmployeeMinute(MeetingMinutesDto meetingDto, ModelMap map) {
        try {
            RestResponseDto restResponseDto = meetingMinuteMinuteService.save(meetingDto);
            UUID id = ((MeetingMinutesDto) restResponseDto.getDetail()).getId();
            return "redirect:/meetingMinutes/show/" + id;
        } catch (Exception e) {
            log.error("error creating minute", e);
            map.addAttribute(ParameterConstant.ERROR, "error creating minute");
        }
        return "meetingMinute/form";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable UUID id, ModelMap map) {
        try {
            map.addAttribute(ParameterConstant.MINUTE, meetingMinuteMinuteService.findById(id));
        } catch (DataNotFoundException ex) {
            log.debug("DataNotFoundException on meeting minute show page", ex);
            return "error/unauthorized";
        } catch (Exception ex) {
            log.error("error on meeting minute show page", ex);
            return "error/internalServer";
        }
        return "meetingMinute/view";
    }
}

