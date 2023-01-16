package com.makalu.hrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/meetingMinutes/bod")
@Controller
public class BodMeetingMinuteController {





    public String list(ModelMap map){
        //all meeting minute list where type == bod
        return "meetingMinute/list";
    }

    public String create(ModelMap map){
        //all user list where type = SA
        return "meetingMinute/create";
    }

    public String show(ModelMap map){
        //all meeting minute  where type == bod and id = id
        return "meetingMinute/create";
    }
}
