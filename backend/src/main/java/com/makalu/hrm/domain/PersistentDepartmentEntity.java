package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "department")
@Data
@EqualsAndHashCode(callSuper=false)
public class PersistentDepartmentEntity extends AbstractEntity {
    @Column(nullable = false,unique = true)
    private  String title;

    @Column(nullable = false,unique = true)
    private String detail;

    @Column(nullable = false,unique = true)
    private String departmentCode;

}
