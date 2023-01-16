package com.makalu.hrm.controller;

import com.makalu.hrm.constant.ParameterConstant;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.model.MeetingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/meetingMinutes")
@Controller
public class EmployeeMeetingMinuteController {

    @GetMapping("/list")
    public String showmeeting(ModelMap map){
List<String> list=new ArrayList<>();
list.add("hellow");
map.addAttribute("Testlist",list);
map.addAttribute(ParameterConstant.MEETING_TYPE.toUpperCase(), MeetingType.EMPLOYEE.name().toUpperCase());

        return "meetingMinute/list";
    }


//    public String list(ModelMap map){
//        //all meeting minute list where type == employee
//        return "meetingMinute/list";
//    }
//

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<MeetingDto> create(MeetingDto meetingDto){
        return ResponseEntity.ok(meetingDto);
    }
//
//    public String show(@PathVariable("id") UUID id, ModelMap map){
//        //all meeting minute  where type == employee and id = id
//        return "meetingMinute/create";
//    }
}
