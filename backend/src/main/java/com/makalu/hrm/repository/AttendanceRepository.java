package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentAttendanceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<PersistentAttendanceEntity, UUID>, JpaSpecificationExecutor<PersistentAttendanceEntity> {


    List<PersistentAttendanceEntity> findAllByUser_Id(UUID userId, Pageable pageable);
    List<PersistentAttendanceEntity> findAllByCreatedDate(Date date);




}
