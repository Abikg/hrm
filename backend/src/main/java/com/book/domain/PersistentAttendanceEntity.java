package com.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="attendance")
public class PersistentAttendanceEntity extends  AbstractEntity {


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "punch_in_date")
    private Date pushInDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "punch_out_date")
    private Date pushOutDate = new Date();

    private String punchInIp;
    private String punchOutIp;




}
