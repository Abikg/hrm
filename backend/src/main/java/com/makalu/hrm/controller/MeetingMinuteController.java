package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.MeetingMinutesDto;

import com.makalu.hrm.service.MeetingMinuteService;

import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@RequestMapping("/meetingMinutes")
@Controller
@RequiredArgsConstructor
public class MeetingMinuteController {
private final MeetingMinuteService meetingMinuteMinuteService;
private  final UserService userService;


    @GetMapping("/employeeForm/{meetingtype}")
        public String showform(@PathVariable MeetingType meetingtype,ModelMap map){
    if(MeetingType.EMPLOYEE.name().equals(meetingtype.name())){

    map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(),meetingtype.name().toUpperCase());
    map.addAttribute("attendedBy",userService.findAllByUserType(UserType.EMPLOYEE));

    }else if(MeetingType.BOD.name().equals(meetingtype.name())){


        map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(),meetingtype.name().toUpperCase());
        map.addAttribute("attendedBy",userService.findAllByUserType(UserType.SUPER_ADMIN));
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

   public String create(MeetingMinutesDto meetingDto, @RequestParam("attendedByy") List<UUID> attendById, ModelMap map) {
        if (meetingDto.getMeetingType().name().toUpperCase().equals(MeetingType.BOD.name().toUpperCase())) {
            if (AuthenticationUtils.getCurrentUser().getUserType().equals(UserType.SUPER_ADMIN)) {

    map.addAttribute(ParameterConstant.RESPONSE,meetingMinuteMinuteService.save(meetingDto,attendById));


               return "meetingMinute/meetingForm";
            }
        }
        map.addAttribute(ParameterConstant.RESPONSE,meetingMinuteMinuteService.save(meetingDto,attendById));

        return "meetingMinute/meetingForm";

        }




    @GetMapping("/showMinute/{id}")
    public String show(@PathVariable UUID id,ModelMap map){

    map.addAttribute(ParameterConstant.MINUTE,meetingMinuteMinuteService.findById(id).getDetail());
     return "meetingMinute/minute";
    }


}

