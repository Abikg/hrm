package com.makalu.hrm.validation.error;

import lombok.Data;

import java.util.Date;

@Data
public class MeetingMinuteError {

    boolean valid;

    String title;

    String minute;

    Date date;

    String generalmessage;

}
