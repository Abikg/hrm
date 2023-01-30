package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EmployeeImageRepository extends JpaRepository<PersistentEmployeeImageEntity, UUID> {

//    @Query("from PersistentEmployeeImageEntity where id = ?1")
//    PersistentEmployeeImageEntity getById(UUID id);
}
