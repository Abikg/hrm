package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeImageRepository extends JpaRepository<PersistentEmployeeImageEntity, UUID> {

    PersistentEmployeeImageEntity findByName(String name);
}
