package com.makalu.hrm.service.impl;

import com.makalu.hrm.converter.MeetingMinuteConverter;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.MeetingMinuteRepository;
import com.makalu.hrm.service.MeetingMinuteService;
import com.makalu.hrm.validation.MeetingMinuteValidation;
import com.makalu.hrm.validation.error.MeetingMinuteError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
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

        try {

            MeetingMinuteError error = meetingMinuteValidation.validateOnSave(meetingDto);

            if (!error.isValid()) {
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", meetingDto));
            }

            return RestResponseDto.INSTANCE().success().detail(meetingMinuteConverter.convertToDto(meetingMinuteRepository.saveAndFlush(meetingMinuteConverter.convertToEntity(meetingDto))));
        } catch (Exception ex) {
            log.error("Error while creating meetiing", ex);
            return RestResponseDto.INSTANCE().internalServerError().detail(meetingDto);
        }

    }


    @Override
    public List<MeetingMinutesDto> findAll(MeetingType meetingType) {
        return meetingMinuteConverter.convertToDtoList(meetingMinuteRepository.findByMeetingType(meetingType));
    }

    @Override
    public RestResponseDto findById(UUID id) {
        try {
            return RestResponseDto.INSTANCE().success().detail(meetingMinuteConverter.convertToDto(meetingMinuteRepository.findById(id).get()));
        } catch (Exception ex) {
            return RestResponseDto.INSTANCE().message("error occured fetching meeting with id" + id);
        }
    }
}
