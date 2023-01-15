package com.makalu.hrm.service;

import com.makalu.hrm.model.AttendanceDto;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {

    List<AttendanceDto> findAll();

    void punchIn(String ip);

    void punchOut(String ip);

    boolean isValidToPunchIn(UUID userId);

    boolean isValidToPunchOut(UUID userId);
}
