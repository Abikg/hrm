package com.book.repository;

import com.book.domain.PersistentRoleEntity;
import com.book.domain.PersistentUserEntity;
import com.book.domain.PersistentUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<PersistentUserRoleEntity, String> {

    Optional<PersistentUserRoleEntity> findById(String userId);

    List<PersistentUserRoleEntity> findAllByUser(PersistentUserEntity user);

    List<PersistentUserRoleEntity> findAllByUser_Id(String userId);

    @Query("select ur.role from PersistentUserRoleEntity ur where ur.user.id=?1")
    List<PersistentRoleEntity> findAllRoleByUser_Id(String userId);

}
