package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.service.MeetingMinuteService;

import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;


@RequestMapping("/meetingMinutes")
@Controller
@RequiredArgsConstructor
public class MeetingMinuteController {
private final MeetingMinuteService meetingMinuteMinuteService;


    @GetMapping("/employeeForm/{meetingtype}")
        public String showform(@PathVariable MeetingType meetingtype,ModelMap map){
    if(MeetingType.EMPLOYEE.name().equals(meetingtype.name())){
    map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(),meetingtype.name().toUpperCase());
    }else if(MeetingType.BOD.name().equals(meetingtype.name())){
        map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(),meetingtype.name().toUpperCase());
    }

   return "meetingMinute/meetingForm";
}

    @GetMapping("/employees/list")
    public String showEmployeesMeeting(ModelMap map){

   map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), MeetingType.EMPLOYEE.name().toUpperCase());
   map.addAttribute(ParameterConstant.MEETING_LIST,meetingMinuteMinuteService.findAll(MeetingType.EMPLOYEE));
        return "meetingMinute/list";
    }



    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/bod/list")
    public String List(ModelMap map){
        map.addAttribute(ParameterConstant.MEETING_LIST,meetingMinuteMinuteService.findAll(MeetingType.BOD));
        map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), MeetingType.BOD.name().toUpperCase());
        return "meetingMinute/list";
    }




    @PostMapping("/save")
    @ResponseBody
   public ResponseEntity<RestResponseDto> create(MeetingMinutesDto meetingDto) {
        if (meetingDto.getMeetingType().name().toUpperCase().equals(MeetingType.BOD.name().toUpperCase())) {
            if (AuthenticationUtils.getCurrentUser().getUserType().equals(UserType.SUPER_ADMIN.name())) {
                return ResponseEntity.ok(meetingMinuteMinuteService.save(meetingDto));
            }
        }
            return ResponseEntity.ok(meetingMinuteMinuteService.save(meetingDto));
        }




    @GetMapping("/showMinute/{id}")
    public String show(@PathVariable UUID id,ModelMap map){

    map.addAttribute("Minute",meetingMinuteMinuteService.findById(id).getDetail());
     return "meetingMinute/minute";
    }


}

