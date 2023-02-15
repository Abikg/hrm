package com.makalu.hrm.domain;

import com.makalu.hrm.utils.UUIDGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "department")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersistentDepartmentEntity {

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

    @Column(nullable = false, unique = true)
    @FullTextField(analyzer = "ngram")
    //@KeywordField(normalizer = "lowercase", name="title_key",sortable= Sortable.YES)
    private String title;

    @Column(nullable = false, unique = true)
    private String detail;

    @Column(nullable = false, unique = true)
    @FullTextField(analyzer = "ngram")
    //@KeywordField(normalizer = "lowercase", name="departmentCode_key",sortable= Sortable.YES)
    private String departmentCode;

    public PersistentDepartmentEntity() {

    }

    private void setId(UUID id) {
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
