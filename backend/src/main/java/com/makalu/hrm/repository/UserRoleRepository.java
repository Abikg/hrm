package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentRoleEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.domain.PersistentUserRoleEntity;
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
