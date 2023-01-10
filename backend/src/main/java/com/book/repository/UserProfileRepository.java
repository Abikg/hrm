package com.book.repository;

import com.book.domain.PersistentUserEntity;
import com.book.domain.PersistentUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<PersistentUserProfileEntity, String> {

    PersistentUserProfileEntity findByUser(PersistentUserEntity user);

    PersistentUserProfileEntity findByUser_Id(String userId);

}
