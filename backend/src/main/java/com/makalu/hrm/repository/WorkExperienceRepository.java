package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentWorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkExperienceRepository extends JpaRepository<PersistentWorkExperienceEntity, UUID> {
}
