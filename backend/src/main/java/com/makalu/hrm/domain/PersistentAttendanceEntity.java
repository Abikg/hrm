package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "attendance")
public class PersistentAttendanceEntity extends AbstractEntity {


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "punch_in_date")
    private Date punchInDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "punch_out_date")
    private Date punchOutDate;

    private String punchInIp;

    private String punchOutIp;

    @Column(name = "total_worked_hours")
    private double totalWorkedHours = 0;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private PersistentUserEntity user;

}
