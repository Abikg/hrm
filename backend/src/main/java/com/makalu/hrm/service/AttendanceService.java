package com.makalu.hrm.service;

import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {


    RestResponseDto Filter(AttendanceDto attendanceDto);

    RestResponseDto findAllUserAttendance(int page);

    void punchIn(String ip);

    void punchOut(String ip);

    boolean isValidToPunchIn(UUID userId);

    boolean isValidToPunchOut(UUID userId);
}
