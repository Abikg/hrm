package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersistentPositionEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String detail;

}
