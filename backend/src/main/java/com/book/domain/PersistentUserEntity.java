package com.book.domain;

import com.book.enumconstant.UserType;
import com.book.utils.UUIDGenerator;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "auth_user", indexes = { @Index(name = "user_index_username", columnList = "username")})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersistentUserEntity extends AbstractEntity{

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    boolean enabled = true;

    @Column(nullable = false)
    boolean accountExpired = false;

    @Column(nullable = false)
    boolean accountLocked = false;

    @Column(nullable = false)
    boolean passwordExpired = false;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

}
