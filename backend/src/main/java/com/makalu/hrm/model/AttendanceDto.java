package com.book.model;


import lombok.Data;

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