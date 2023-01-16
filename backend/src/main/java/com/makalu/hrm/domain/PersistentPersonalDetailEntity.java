package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal_detail")
@Data
@EqualsAndHashCode(callSuper=false)
public class PersistentPersonalDetailEntity extends AbstractEntity{
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String maritalStatus;
}
