package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@Table(name = "employee")
@EqualsAndHashCode(callSuper=false)
public class PersistentEmployeeEntity extends AbstractEntity {

    private String fullname;

    private String address;

    private String phone;

    private String email;

    private String empImage;


    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_position",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "position_id") }
    )
    private List<PersistentPositionEntity> position;

    @ManyToMany(cascade = { CascadeType.ALL } , fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_department",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "department_id") }
    )
    private List<PersistentDepartmentEntity> department;

    @OneToOne
    private PersistentUserEntity user;


    @OneToOne
    private PersistentEmployeeImageEntity image;
}
