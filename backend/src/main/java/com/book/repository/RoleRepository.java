package com.book.repository;

import com.book.domain.PersistentRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<PersistentRoleEntity, String> {

    Optional<PersistentRoleEntity> findById(String userId);

    Optional<PersistentRoleEntity> findByAuthority(String authority);

}
