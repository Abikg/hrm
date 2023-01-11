package com.makalu.hrm.repository;


import com.makalu.hrm.domain.PersistentAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<PersistentAttendanceEntity, UUID> {


}
