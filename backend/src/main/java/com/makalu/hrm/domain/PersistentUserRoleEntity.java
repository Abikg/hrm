package com.makalu.hrm.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_role")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersistentUserRoleEntity extends AbstractEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private PersistentUserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersistentRoleEntity role;
}
