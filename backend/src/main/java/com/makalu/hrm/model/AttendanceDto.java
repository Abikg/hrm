package com.makalu.hrm.model;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class AttendanceDto {
    private UUID id;
    private Date punchInDate;
    private Date punchOutDate;
    private String punchInIp;
    private String punchOutIp;
    private String totalWorkedHours;
    private UserDTO user;
    private Date fromDate;
    private Date toDate;
    private  String time;


}
