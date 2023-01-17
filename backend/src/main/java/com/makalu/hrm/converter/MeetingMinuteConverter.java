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

@Component
@RequiredArgsConstructor
public class MeetingMinuteConverter   extends Convertable<PersistentMeetingMinutesEntity, MeetingMinutesDto> {


    private final UserConverter userConverter;
    private  final AttendanceRepository attendanceRepository;
    private  final UserRepository userRepository;

    @Override
    public PersistentMeetingMinutesEntity convertToEntity(MeetingMinutesDto dto) {
      PersistentMeetingMinutesEntity entity=new PersistentMeetingMinutesEntity();
      entity.setTitle(dto.getTitle());
      entity.setMeetingDate(dto.getMeetingDate());
      entity.setMinutes(dto.getMinutes());
      entity.setMeetingType(dto.getMeetingType());
      //getting employees who are present today

        List<PersistentUserEntity> PresentList=new ArrayList<>();

        List<PersistentAttendanceEntity> attendanceToday=attendanceRepository.findAllByCreatedDate(new Date());

        if(dto.getMeetingType().name().toUpperCase().equals(MeetingType.EMPLOYEE.name().toUpperCase()))

            for(PersistentAttendanceEntity attendance:attendanceToday) {
                if (attendance.getUser().getUserType().equals(UserType.EMPLOYEE)) {
                    PresentList.add(attendance.getUser());
                }
            }
        else if (dto.getMeetingType().name().toUpperCase().equals(MeetingType.BOD.name().toUpperCase())) {
            for(PersistentAttendanceEntity attendance:attendanceToday){
              if(attendance.getUser().getUserType().equals(UserType.SUPER_ADMIN)){
                    PresentList.add(attendance.getUser());
                }}}

           entity.setAttendedBy(PresentList);
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
