package com.book.repository;

import com.book.domain.PersistentDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<PersistentDepartmentEntity, UUID> {

    @Query("from PersistentDepartmentEntity where title = ?1 or departmentCode =?2")
    PersistentDepartmentEntity findByTitleOrDepartmentCode(String title, String code);

}
