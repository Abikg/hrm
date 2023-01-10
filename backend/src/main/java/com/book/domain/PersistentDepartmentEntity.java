package com.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

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
