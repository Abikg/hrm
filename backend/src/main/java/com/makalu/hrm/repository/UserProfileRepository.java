package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.domain.PersistentUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<PersistentUserProfileEntity, String> {

    PersistentUserProfileEntity findByUser(PersistentUserEntity user);

    PersistentUserProfileEntity findByUser_Id(String userId);

}
