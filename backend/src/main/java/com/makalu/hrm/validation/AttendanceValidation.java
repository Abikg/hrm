package com.makalu.hrm.validation;

import com.book.model.AttendanceDto;


import com.makalu.hrm.validation.error.AttendanceError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceValidation {

    private final AttendanceError attendanceError=new AttendanceError();
    public AttendanceError validateOnSave(AttendanceDto attendanceDto){
        boolean isValid =validateIp(attendanceDto.getPunchInIp());



        attendanceError.setValid(isValid);
        return attendanceError;
    }



    public AttendanceError validateOnUpdate(AttendanceDto attendanceDto){
        boolean isValid =validateIp(attendanceDto.getPunchOutIp());
          attendanceError.setValid(isValid);
        return attendanceError;
    }



    private boolean validateIp(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            attendanceError.setTitle("punch  ip-adress is needed");
            return false;
        }
        return true;
    }



}
