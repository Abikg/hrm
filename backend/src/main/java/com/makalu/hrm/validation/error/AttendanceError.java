package com.makalu.hrm.validation.error;

import lombok.Data;

@Data
public class AttendanceError {


    private boolean valid;

    private String title;

    private String detail;

    private String generalMessage;


}
