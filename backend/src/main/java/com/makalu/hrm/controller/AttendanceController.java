package com.makalu.hrm.controller;



import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.impl.AttendanceServiceImp;

import com.makalu.hrm.utils.IPUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor

public class AttendanceController {
    private final AttendanceServiceImp attendanceServiceImp;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;


    @GetMapping("/punchin")

    public ResponseEntity<RestResponseDto> punchIn(HttpServletRequest request) {
        AttendanceDto attendanceDto = new AttendanceDto();

        //getting client ip
        String clientIp = IPUtils.getClientIp(request);


        if (IPUtils.ismakaluNetwork(clientIp)) {
            attendanceDto.setPunchInIp(clientIp);
            return ResponseEntity.ok().body(attendanceServiceImp.save(attendanceDto));

        }
        return ResponseEntity.ok().body(RestResponseDto.INSTANCE().message("yo cannot login outside of office"));


    }




    @GetMapping("/punchout")
    public ResponseEntity<RestResponseDto> punchout(HttpServletRequest request) {

        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setPunchOutDate(new Date());
        attendanceDto.setPunchOutIp(IPUtils.getClientIp(request));

        return ResponseEntity.ok().body(attendanceServiceImp.update(attendanceDto));


    }

    @GetMapping("/list")
    public String list(ModelMap modelMap){
        modelMap.addAttribute( "attendanceDtoList",attendanceServiceImp.findAll());

     return "attendance/list";
    }

//    @GetMapping
//    public String getTodayAttendance(ModelMap modelMap){
//
//        List<AttendanceDto> attendanceDtoList=new ArrayList<>();
//    //    AttendanceDto attendanceDto=
//
//        modelMap.addAttribute("")
//
//    }

















}
