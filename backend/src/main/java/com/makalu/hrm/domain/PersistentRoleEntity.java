package com.makalu.hrm.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersistentRoleEntity extends AbstractEntity {

    @Column(name = "authority", nullable = false, unique = true, length = 50)
    private String authority;
}
