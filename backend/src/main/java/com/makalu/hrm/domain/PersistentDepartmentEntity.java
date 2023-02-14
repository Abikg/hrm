package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersistentDepartmentEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String detail;

    @Column(nullable = false, unique = true)
    private String departmentCode;

}
