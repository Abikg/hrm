package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<PersistentEmployeeEntity, UUID> {

    @Query("from PersistentEmployeeEntity where phone = ?1 or email = ?2")
    PersistentEmployeeEntity findByPhoneOrEmail(String phone, String email);


}
