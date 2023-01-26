package com.makalu.hrm.service;

import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import java.util.UUID;

public interface AttendanceService {


    RestResponseDto Filter(AttendanceDto attendanceDto);
    void punchIn(String ip);

    void punchOut(String ip);

    boolean isValidToPunchIn(UUID userId);

    boolean isValidToPunchOut(UUID userId);
}
