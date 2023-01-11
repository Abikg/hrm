package com.makalu.hrm.controller;


import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.impl.AttendanceServiceImp;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor

public class AttendanceController {
private final AttendanceServiceImp attendanceServiceImp;
private final AuthenticationUtils authenticationUtils;

private  final UserRepository userRepository;
private final AttendanceRepository attendanceRepository;



//    @PostMapping
//    public ResponseEntity<RestResponseDto> save(AttendanceDto attendanceDto){
//        return ResponseEntity.ok(attendanceServiceImp.save(attendanceDto));
//    }
//    @GetMapping
//    public ResponseEntity<RestResponseDto> getAll()
//    {
//        return ResponseEntity.ok(RestResponseDto.INSTANCE().detail(attendanceServiceImp.findAll()));
//
//
//
//    }
//
//    @PutMapping
//    public ResponseEntity<RestResponseDto> update(AttendanceDto attendanceDto){
//        return ResponseEntity.ok(attendanceServiceImp.update(attendanceDto));
//
//    }
//@PreAuthorize("hasRole('SUPER_ADMIN')")

    @PostMapping("/attendance")

    public PersistentAttendanceEntity hit() {
         com.book.model.AttendanceDto attendanceDto =new com.book.model.AttendanceDto();
      attendanceDto.setPunchInIp("1.0.0.0.1");

     PersistentUserEntity user=   userRepository.findByUsername("dhirajbadu50@gmail.com").get();
        PersistentAttendanceEntity attendanceEntity = new PersistentAttendanceEntity();

        attendanceEntity.setPunchInIp("1.0.0.0.0.0");
        attendanceEntity.setUser(user);


        return attendanceRepository.save(attendanceEntity);


    }



//UserDetails userDetails=AuthenticationUtils.getCurrentUser();
//    PersistentUserEntity user= userRepository.findByUsername(userDetails.getUsername()).get();



//return  userDetails.getUsername();














}
