package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<PersistentPositionEntity, UUID> {

    PersistentPositionEntity findByTitle(String title);

}
