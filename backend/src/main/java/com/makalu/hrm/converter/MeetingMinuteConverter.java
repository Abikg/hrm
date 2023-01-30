package com.makalu.hrm.converter;


import com.makalu.hrm.domain.PersistentMeetingMinutesEntity;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MeetingMinuteConverter extends Convertable<PersistentMeetingMinutesEntity, MeetingMinutesDto> {
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    public PersistentMeetingMinutesEntity convertToEntity(MeetingMinutesDto dto) {
        PersistentMeetingMinutesEntity entity = new PersistentMeetingMinutesEntity();
        entity.setTitle(dto.getTitle());
        entity.setMeetingDate(dto.getMeetingDate());
        entity.setMinutes(dto.getMinutes());
        entity.setMeetingType(dto.getMeetingType());
        entity.setAttendedBy(userRepository.findAllById(dto.getAttendedByIdsFromView()));
        entity.setCreatedBy(userRepository.findByUsername(AuthenticationUtils.getCurrentUser().getUsername()).get());
        return entity;
    }

    @Override
    public PersistentMeetingMinutesEntity copyConvertToEntity(MeetingMinutesDto dto, PersistentMeetingMinutesEntity entity) {
        return null;
    }

    @Override
    public MeetingMinutesDto convertToDto(PersistentMeetingMinutesEntity entity) {
        if (entity == null)
            return null;

        MeetingMinutesDto dto = MeetingMinutesDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .meetingDate(entity.getMeetingDate())
                .minutes(entity.getMinutes())
                .meetingType(entity.getMeetingType())
                .attendedBy(userConverter.convertToDtoList(entity.getAttendedBy()))
                .createdBy(userConverter.convertToDto(entity.getCreatedBy())).build();
        return dto;
    }

}
