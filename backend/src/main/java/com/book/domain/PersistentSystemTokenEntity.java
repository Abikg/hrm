package com.book.domain;

import com.book.enumconstant.TokenType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "system_token", indexes = {
        @Index(name = "index_system_token", columnList = "token"),
        @Index(name = "index_system_token_associateId", columnList = "associateId"),
})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersistentSystemTokenEntity extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expiration;

    @Column(nullable = false)
    private TokenType tokenType;

    @Column(nullable = false)
    private String associateId;
}
