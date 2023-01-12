package com.makalu.hrm.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


@Data
public class AttendanceDto {


    private UUID id;
    private Date punchInDate ;



    private Date punchOutDate ;

    private String punchInIp;
    private String punchOutIp;

}
