package com.makalu.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.makalu.hrm.enumconstant.EmployeeStatus;
import com.makalu.hrm.model.ContactDetailDTO;
import com.makalu.hrm.model.PersonalDetailDTO;
import com.makalu.hrm.model.WorkExperienceDTO;
import com.makalu.hrm.utils.UUIDGenerator;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.springframework.lang.NonNull;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "employee")
@EqualsAndHashCode(callSuper = false)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@ToString(exclude = {"employeeId"})
@Indexed
public class PersistentEmployeeEntity{

    @Id
    @NonNull
    @Type(type = "uuid-char")
    @KeywordField
    private UUID id;

    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Version
    @Column(name = "version")
    private int version;

   /* @Column(name = "created_by", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private PersistentUserEntity createdBy;*/
    
    @Column(nullable = false)
    @FullTextField(analyzer = "ngram")
    @KeywordField(normalizer = "lowercase", name="employeeId_key",sortable= Sortable.YES)
    private String employeeId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @Column(nullable = false)
    @FullTextField(analyzer = "ngram")
    @KeywordField(normalizer = "lowercase", name="fullname_key",sortable= Sortable.YES)
    private String fullname;

    @Column(nullable = false)
    @FullTextField(analyzer = "ngram")
    @KeywordField(normalizer = "lowercase", name="email_key",sortable= Sortable.YES)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "join_date")
    private Date joinDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "contact_detail")
    private ContactDetailDTO contactDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "personal_detail")
    private PersonalDetailDTO personalDetail;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "work_experience")
    private List<WorkExperienceDTO> workExperience;

    @Column
    private String resignationReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resignation_date")
    private Date resignationDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exit_date")
    private Date exitDate;

    @OneToOne
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private PersistentPositionEntity position;

    @OneToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private PersistentDepartmentEntity department;

    @OneToOne
    private PersistentUserEntity user;

    @OneToOne
    private PersistentUserEntity approvedBy;

    @OneToOne
    private PersistentEmployeeImageEntity image;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private PersistentEmployeeEntity manager;

    @Transient
    @OneToMany(mappedBy = "manager")
    private List<PersistentEmployeeEntity> subordinates;
    public PersistentEmployeeEntity() {

    }


    public String getEmployeeId() {
        return "MS"+this.employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentEmployeeEntity)) return false;
        if (!super.equals(o)) return false;
        PersistentEmployeeEntity employee = (PersistentEmployeeEntity) o;
        return getEmployeeId() == employee.getEmployeeId() && getEmployeeStatus() == employee.getEmployeeStatus() && getFullname().equals(employee.getFullname()) && getEmail().equals(employee.getEmail()) && getJoinDate().equals(employee.getJoinDate()) && Objects.equals(getContactDetail(), employee.getContactDetail()) && Objects.equals(getPersonalDetail(), employee.getPersonalDetail()) && Objects.equals(getWorkExperience(), employee.getWorkExperience()) && Objects.equals(getResignationReason(), employee.getResignationReason()) && Objects.equals(getResignationDate(), employee.getResignationDate()) && Objects.equals(getExitDate(), employee.getExitDate()) && getPosition().equals(employee.getPosition()) && Objects.equals(getUser(), employee.getUser()) && Objects.equals(getApprovedBy(), employee.getApprovedBy()) && Objects.equals(getImage(), employee.getImage()) && Objects.equals(getManager(), employee.getManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEmployeeId(), getEmployeeStatus(), getFullname(), getEmail(), getJoinDate(), getContactDetail(), getPersonalDetail(), getWorkExperience(), getResignationReason(), getResignationDate(), getExitDate(), getPosition(), getUser(), getApprovedBy(), getImage(), getManager());
    }
    public void setId(UUID id) {
        this.id = id;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = new Date();
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
        setId(UUIDGenerator.INSTANCE().newUUID());
    }
}
