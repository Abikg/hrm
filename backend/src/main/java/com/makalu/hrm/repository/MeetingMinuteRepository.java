package com.makalu.hrm.repository;

import com.makalu.hrm.domain.PersistentMeetingMinutesEntity;
import com.makalu.hrm.enumconstant.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingMinuteRepository extends JpaRepository<PersistentMeetingMinutesEntity, UUID> {

 List<PersistentMeetingMinutesEntity> findByMeetingType(MeetingType meetingType);

 Optional<PersistentMeetingMinutesEntity> findById(UUID id);

}
