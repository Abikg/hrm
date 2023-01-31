package com.makalu.hrm.validation.error;

import lombok.Data;

@Data
public class AttendanceError {

    String datetimeRange;

    private boolean valid;
}
