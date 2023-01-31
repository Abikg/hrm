package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<PersistentDepartmentEntity, UUID> {

    @Query("from PersistentDepartmentEntity where title = ?1 or departmentCode =?2")
    PersistentDepartmentEntity findByTitleOrDepartmentCode(String title, String code);

    @Override
    List<PersistentDepartmentEntity> findAllById(Iterable<UUID> uuids);
}
