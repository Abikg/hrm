package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.MeetingMinuteConverter;
import com.makalu.hrm.domain.PersistentMeetingMinutesEntity;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.exceptions.DataNotFoundException;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.MeetingMinuteRepository;
import com.makalu.hrm.service.MeetingMinuteService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.validation.MeetingMinuteValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingMinuteMinuteServiceImp implements MeetingMinuteService {

    private final MeetingMinuteConverter meetingMinuteConverter;
    private final MeetingMinuteRepository meetingMinuteRepository;
    private final MeetingMinuteValidation meetingMinuteValidation;

    @Override
    public RestResponseDto save(MeetingMinutesDto meetingDto) {
        return RestResponseDto.INSTANCE().success().detail(meetingMinuteConverter.convertToDto(meetingMinuteRepository.saveAndFlush(meetingMinuteConverter.convertToEntity(meetingDto))));
    }

    @Override
    public List<MeetingMinutesDto> findAll(MeetingType meetingType) {
        return meetingMinuteConverter.convertToDtoList(meetingMinuteRepository.findByMeetingType(meetingType));
    }

    @Override
    public MeetingMinutesDto findById(UUID id) throws DataNotFoundException {
        Optional<PersistentMeetingMinutesEntity> meetingMinutes = meetingMinuteRepository.findById(id);
        if (meetingMinutes.isEmpty()) {
            throw new DataNotFoundException("meeting minute not found with id " + id);
        }
        if (meetingMinutes.get().getMeetingType().equals(MeetingType.BOD) && AuthenticationUtils.hasRole(UserType.SUPER_ADMIN.name().toUpperCase())) {
            return meetingMinuteConverter.convertToDto(meetingMinutes.get());
        }
        if (meetingMinutes.get().getMeetingType().equals(MeetingType.EMPLOYEE)) {
            return meetingMinuteConverter.convertToDto(meetingMinutes.get());
        }
        throw new DataNotFoundException("this meeting minute is not accessible to this user (id =>)" + id);
    }
}
