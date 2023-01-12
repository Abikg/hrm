package com.makalu.hrm.service.impl;





import com.makalu.hrm.converter.AttendanceConverter;
import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.validation.AttendanceValidation;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private  final AttendanceConverter attendanceConverter;
    private  final AttendanceRepository attendanceRepository;
    private final AttendanceValidation attendanceValidation;
    private  final UserRepository userRepository;



    @Override
    public List<AttendanceDto> findAll() {
        return attendanceConverter.convertToDtoList(attendanceRepository.findAll());
    }

    @Override
    @Transactional
    public RestResponseDto save(@NonNull  AttendanceDto attendanceDto) {


        AttendanceError error = attendanceValidation.validateOnSave(attendanceDto);
        if(attendanceValidation.isUserPunchedInToday()){
            return RestResponseDto.INSTANCE().message("User is already punchin today");
        }

        try {
            if (!error.isValid()) {
                return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", attendanceDto));
            }
            return RestResponseDto
                    .INSTANCE()
                    .success()
                    .detail(attendanceConverter.convertToDto(
                            attendanceRepository.saveAndFlush(attendanceConverter.convertToEntity(attendanceDto))
                    ));
        } catch (Exception e) {
            log.error("Error while creating attendance", e);
            return RestResponseDto
                    .INSTANCE()
                    .internalServerError()
                    .detail(attendanceDto);
        }
    }
    @Override
    @Transactional
    public RestResponseDto update(AttendanceDto attendanceDto) {

            try {
                AttendanceError error = attendanceValidation.validateOnUpdate(attendanceDto);
                if (!error.isValid()) {
                    return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", attendanceDto));
                }

                PersistentAttendanceEntity attendance = attendanceRepository.findByCreatedDateAndUser(
                        new Date(), userRepository.findByUsername(
                                AuthenticationUtils.getCurrentUser().getUsername())
                                .get()
                ).get();
                if(attendance==null){
                    return RestResponseDto.INSTANCE().message("you didnot press hit button today").detail(attendanceDto);

                }



               attendance = attendanceConverter.copyConvertToEntity(attendanceDto, attendance);
                return RestResponseDto.INSTANCE()
                        .success()
                        .detail(attendanceConverter.convertToDto(
                                attendanceRepository.saveAndFlush(attendance)
                        ));
            }catch (Exception e){
                log.error("Error while creating attendance", e);
                return RestResponseDto.INSTANCE()
                        .internalServerError()
                        .detail(attendanceDto);
            }
    }



  //  @Override
//    public List<DepartmentDTO> list() {
//        return departmentConverter.convertToDtoList(departmentRepository.findAll());
//    }


}
