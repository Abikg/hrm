package com.makalu.hrm.service;

import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.exceptions.DataNotFoundException;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.model.RestResponseDto;
import java.util.List;
import java.util.UUID;

public interface MeetingMinuteService {

    public RestResponseDto save(MeetingMinutesDto meetingDto);

    public List<MeetingMinutesDto> findAll(MeetingType meetingType);

    public MeetingMinutesDto findById(UUID id) throws DataNotFoundException;

}
