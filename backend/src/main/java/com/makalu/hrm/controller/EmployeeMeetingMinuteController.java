package com.makalu.hrm.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("meetingMinute")
public class EmployeeMeetingMinuteController {

    public String list(ModelMap map){
        //all meeting minute list where type == employee
        return "meetingMinute/list";
    }

    public String create(ModelMap map){
        //all user list
        return "meetingMinute/create";
    }

    public String show(@PathVariable("id") UUID id, ModelMap map){
        //all meeting minute  where type == employee and id = id
        return "meetingMinute/create";
    }
}
