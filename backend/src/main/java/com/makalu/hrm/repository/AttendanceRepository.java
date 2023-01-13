package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<PersistentAttendanceEntity, UUID> {

    Optional<PersistentAttendanceEntity> findByCreatedDateAndUser(Date date, PersistentUserEntity user);

    List<PersistentAttendanceEntity> findAllByUser_Id(UUID userId, Pageable pageable);

}
