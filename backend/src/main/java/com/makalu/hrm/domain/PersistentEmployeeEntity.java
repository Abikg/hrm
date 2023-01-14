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

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;


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
