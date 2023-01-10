package com.makalu.hrm.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import com.vladmihalcea.hibernate.type.json.JsonType;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "worker_profile")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class PersistentWorkerProfileEntity extends AbstractEntity{

    //todo think about rate
    private String firstName;
    private String middleName;
    private String lastName;
    private String district;
    private String city;
    private String street;
    private String mobileNumber;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    List<PersistentWorkCategoryEntity> workCategory;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb", name = "experience_list", nullable = false)
    List<ExperienceModel> experienceList;

}
