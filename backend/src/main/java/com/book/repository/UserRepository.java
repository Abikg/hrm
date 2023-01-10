package com.book.repository;

import com.book.domain.PersistentUserEntity;
import com.book.enumconstant.UserType;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PersistentUserEntity, String> {

    Optional<PersistentUserEntity> findById(String userId);

    Optional<PersistentUserEntity> findByUsername(String username);

//    @Query("select u from PersistentUserEntity u where u.id=?1 and u.userType=?2")
    PersistentUserEntity findByIdAndUserType(String userId,UserType userType);
}
