package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "department")
@Data
@EqualsAndHashCode(callSuper=false)
public class PersistentDepartmentEntity extends AbstractEntity {

    private  String title;

    private String detail;

    private String departmentCode;

//    @ManyToMany(mappedBy = "department", fetch = FetchType.LAZY)
//    private List<PersistentEmployeeEntity> employee;
}
