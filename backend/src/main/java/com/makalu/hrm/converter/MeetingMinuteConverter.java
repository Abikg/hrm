package com.makalu.hrm.converter;

import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.domain.PersistentMeetingMinutesEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.enumconstant.MeetingType;
import com.makalu.hrm.enumconstant.UserType;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MeetingMinuteConverter   extends Convertable<PersistentMeetingMinutesEntity, MeetingMinutesDto> {


    private final UserConverter userConverter;
    private  final AttendanceRepository attendanceRepository;
    private  final UserRepository userRepository;


    public PersistentMeetingMinutesEntity convertToEntity(MeetingMinutesDto dto,List<UUID> attendedById) {
      PersistentMeetingMinutesEntity entity=new PersistentMeetingMinutesEntity();
      entity.setTitle(dto.getTitle());
      entity.setMeetingDate(dto.getMeetingDate());
      entity.setMinutes(dto.getMinutes());
      entity.setMeetingType(dto.getMeetingType());
      entity.setAttendedBy(userRepository.findAllById(attendedById));
      List<PersistentUserEntity> list=userRepository.findAllById(attendedById);
       entity.setAttendedBy(list);

      entity.setCreatedBy(userRepository.findByUsername(AuthenticationUtils.getCurrentUser().getUsername()).get());


      return  entity;
    }


    @Override
    public PersistentMeetingMinutesEntity  copyConvertToEntity(MeetingMinutesDto dto, PersistentMeetingMinutesEntity entity) {
        return null;
    }


    @Override
    public MeetingMinutesDto convertToDto(PersistentMeetingMinutesEntity  entity) {

        if (entity == null)
            return null;

        MeetingMinutesDto dto=MeetingMinutesDto.builder()
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
