package com.makalu.hrm.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_profile")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersistentUserProfileEntity extends AbstractEntity{

    @OneToOne(fetch = FetchType.LAZY)
    private PersistentUserEntity user;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false, length = 20)
    private String mobileNumber;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 10)
    private String gender;
}
