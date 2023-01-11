package com.makalu.hrm.service;


import com.book.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;


import java.util.List;

public interface AttendanceService {


    List<AttendanceDto> findAll();
    RestResponseDto save(AttendanceDto attendanceDto);
    RestResponseDto update(AttendanceDto attendanceDto);

}
