package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "work_experience")
@Data
@EqualsAndHashCode(callSuper=false)
public class PersistentWorkExperienceEntity extends AbstractEntity{
    @Column(nullable = false)
    private String previousCompany;

    @Column(nullable = false)
    private String jobTitle;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;


    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
