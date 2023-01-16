package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentPersonalDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalDetailRepository extends JpaRepository<PersistentPersonalDetailEntity, UUID> {
}
