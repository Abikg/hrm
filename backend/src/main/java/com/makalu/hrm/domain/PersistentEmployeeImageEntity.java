package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="employee_image")
public class PersistentEmployeeImageEntity extends AbstractEntity {

    private String name;

    private String type;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;
}
