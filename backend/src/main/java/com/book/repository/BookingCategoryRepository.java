package com.book.repository;

import com.book.domain.PersistentWorkCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCategoryRepository extends JpaRepository<PersistentWorkCategoryEntity, String> {

    PersistentWorkCategoryEntity findByName(String name);
}
