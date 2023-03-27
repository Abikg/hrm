package com.makalu.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "department")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"manager"})
public class PersistentDepartmentEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String detail;

    @Column(nullable = false, unique = true)
    private String departmentCode;

    @OneToOne
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private PersistentEmployeeEntity manager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentDepartmentEntity)) return false;
        if (!super.equals(o)) return false;
        PersistentDepartmentEntity that = (PersistentDepartmentEntity) o;
        return Objects.equals(getTitle(), that.getTitle()) && getDetail().equals(that.getDetail()) && getDepartmentCode().equals(that.getDepartmentCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle(), getDetail(), getDepartmentCode());
    }
}
