package com.makalu.hrm.domain;

import com.makalu.hrm.model.ContactDetailDTO;
import com.makalu.hrm.model.PersonalDetailDTO;
import com.makalu.hrm.model.WorkExperienceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@Table(name = "employee")
@EqualsAndHashCode(callSuper=false)
public class PersistentEmployeeEntity extends AbstractEntity {

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "contact_detail")
    private ContactDetailDTO contactDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "personal_detail")
    private PersonalDetailDTO personalDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "work_experience")
    private List<WorkExperienceDTO> workExperience;

    @OneToOne
    private PersistentPositionEntity position;

    @OneToOne
    private PersistentDepartmentEntity department;

    @OneToOne
    private PersistentUserEntity user;


    @OneToOne
    private PersistentEmployeeImageEntity image;



}
