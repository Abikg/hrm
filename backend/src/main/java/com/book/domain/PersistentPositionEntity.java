package com.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
