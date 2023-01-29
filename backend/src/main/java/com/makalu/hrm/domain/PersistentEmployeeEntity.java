package com.makalu.hrm.domain;

import com.makalu.hrm.enumconstant.EmployeeStatus;
import com.makalu.hrm.model.ContactDetailDTO;
import com.makalu.hrm.model.PersonalDetailDTO;
import com.makalu.hrm.model.WorkExperienceDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
@Entity
@Data
@Table(name = "employee")
@EqualsAndHashCode(callSuper=false)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@ToString(exclude = {"employeeId"})
public class PersistentEmployeeEntity extends AbstractEntity {

    @Column(nullable = false)
    private long employeeId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String email;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "join_date")
    private Date joinDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "contact_detail")
    private ContactDetailDTO contactDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "personal_detail")
    private PersonalDetailDTO personalDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "work_experience")
    private List<WorkExperienceDTO> workExperience;

    @Column
    private String resignationReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resignation_date")
    private Date resignationDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exit_date")
    private Date exitDate;

    @OneToOne
    private PersistentPositionEntity position;

    @OneToOne
    private PersistentDepartmentEntity department;

    @OneToOne
    private PersistentUserEntity user;

    @OneToOne
    private PersistentUserEntity approvedBy;

    @OneToOne
    private PersistentEmployeeImageEntity image;



    public String getEmployeeId(){
        return new StringBuilder().append("MS").append(employeeId).toString();
    }

}
