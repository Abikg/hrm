package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentEmployeeEntity;
import com.makalu.hrm.domain.PersistentPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<PersistentEmployeeEntity, UUID> {

    @Query("from PersistentEmployeeEntity where phone = ?1 or email =?2")
    PersistentEmployeeEntity findByPhoneOrEmail(String phone,String email);

    PersistentEmployeeEntity findByPhone(String phone);

    PersistentEmployeeEntity findByEmail(String email);
}
