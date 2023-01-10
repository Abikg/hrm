package com.book.domain;

import com.book.utils.UUIDGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class AbstractEntity {

    private static final long serialVersionUID = 8453654076725018243L;

    @Id
    @NonNull
    @Type(type = "uuid-char")
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

    public void setId(UUID id){
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

