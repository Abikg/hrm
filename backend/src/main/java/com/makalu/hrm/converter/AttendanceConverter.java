package com.makalu.hrm.converter;


import com.book.model.AttendanceDto;

import com.makalu.hrm.converter.Convertable;
import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceConverter extends Convertable<PersistentAttendanceEntity, AttendanceDto> {

    private final UserRepository userRepository;
    @Override //for creating
    public PersistentAttendanceEntity convertToEntity(AttendanceDto dto) {

        // getting username of logged user from spring security
        UserDetails userDetails=AuthenticationUtils.getCurrentUser();


        // PersistentUserEntity user= userRepository.findByUsername("dhirajbadu50@gmail.com").get();
        PersistentUserEntity user=userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new RuntimeException("user not loggein"));


        //generate new Attendance
        PersistentAttendanceEntity entity=new PersistentAttendanceEntity();
        entity.setUser(user);


        return this.copyConvertToEntity(dto, entity);
    }


    @Override  //for update only
    public PersistentAttendanceEntity copyConvertToEntity(AttendanceDto dto, PersistentAttendanceEntity entity) {

        if (entity == null || dto == null) {
            return null;
        }


       // entity.setPunchInDate(dto.getPunchInDate());
        entity.setPunchInIp(dto.getPunchInIp());
        entity.setPunchOutDate(dto.getPunchOutDate());
        entity.setPunchOutIp(dto.getPunchOutIp());

        return entity;
    }


    @Override
    public AttendanceDto convertToDto(PersistentAttendanceEntity entity) {

        if (entity == null)
            return null;

        AttendanceDto dto = new AttendanceDto();
        dto.setPunchInDate(dto.getPunchInDate());
        dto.setPunchInIp(dto.getPunchInIp());
        dto.setPunchOutDate(dto.getPunchOutDate());
        dto.setPunchOutIp(dto.getPunchOutIp());
        return dto;
    }



}
