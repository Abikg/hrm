package com.makalu.hrm.controller;


import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.impl.AttendanceServiceImp;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequiredArgsConstructor

public class AttendanceController {
private final AttendanceServiceImp attendanceServiceImp;







    @GetMapping("/attendance")

    public ResponseEntity<RestResponseDto> hit() {
         com.book.model.AttendanceDto attendanceDto =new com.book.model.AttendanceDto();
      attendanceDto.setPunchInIp("1.0.0.0.1");




        return ResponseEntity.ok().body(attendanceServiceImp.save(attendanceDto));


    }

















}
