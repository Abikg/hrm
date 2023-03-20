package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<PersistentEmployeeEntity, UUID> {

    @Query("select e from PersistentEmployeeEntity e where e.email =?1")
    PersistentEmployeeEntity findByEmail(String email);

    @Override
    long count();

    @Query("select  e from PersistentEmployeeEntity e where e.department.id =?1")
    List<PersistentEmployeeEntity> findAllByDepartment(UUID departmentId);
}
