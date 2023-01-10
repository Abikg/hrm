package com.makalu.hrm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Data
@EqualsAndHashCode(callSuper=false)
public class PersistentPositionEntity  extends AbstractEntity{

    private String title;

    private String detail;

//    @ManyToMany(mappedBy = "position" , fetch = FetchType.LAZY)
//    private List<PersistentEmployeeEntity> employee;

}
