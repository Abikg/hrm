package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<PersistentPositionEntity, UUID> {

    PersistentPositionEntity findByTitle(String title);

    @Override
    List<PersistentPositionEntity> findAllById(Iterable<UUID> uuids);
}
