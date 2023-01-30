package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<PersistentEmployeeEntity, UUID> {

    @Query("select e from PersistentEmployeeEntity e where e.email =?1")
    PersistentEmployeeEntity findByEmail(String email);


    @Override
    long count();
}
