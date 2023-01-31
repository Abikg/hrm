package com.makalu.hrm.service;

import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;

import java.util.UUID;

public interface AttendanceService {

    RestResponseDto filter(AttendanceDto attendanceDto);

    void punchIn(String ip);

    RestResponseDto punchOut(String ip);

    boolean isValidToPunchIn(UUID userId);

    RestResponseDto setPunchinAnotherDay(String time, String ip);

    boolean isValidToPunchOut(UUID userId);
}
