package com.makalu.hrm.validation;

import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AttendanceValidation {
    private final AttendanceError attendanceError = new AttendanceError();

    public AttendanceError validateDateRange(AttendanceDto dto) {
        Duration duration = Duration.between(dto.getFromDate().toInstant(), dto.getToDate().toInstant());
        long days = duration.toDays();
        if (days > 365) {
            attendanceError.setDatetimeRange("select range between a year");
            attendanceError.setValid(false);

        } else {
            attendanceError.setValid(true);
        }
        return attendanceError;


    }


}
