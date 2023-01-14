package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<PersistentUserEntity, UUID> {

    Optional<PersistentUserEntity> findById(UUID userId);

    Optional<PersistentUserEntity> findByUsername(String username);

//    @Query("select u from PersistentUserEntity u where u.id=?1 and u.userType=?2")
    PersistentUserEntity findByIdAndUserType(UUID userId, UserType userType);
}
