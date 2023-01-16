package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentContactDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactDetailRepository extends JpaRepository<PersistentContactDetailEntity, UUID> {
}
