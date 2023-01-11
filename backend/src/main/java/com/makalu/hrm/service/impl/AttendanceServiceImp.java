package com.makalu.hrm.service.impl;



import com.book.model.AttendanceDto;

import com.makalu.hrm.converter.AttendanceConverter;
import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.validation.AttendanceValidation;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private  final AttendanceConverter attendanceConverter;
    private  final AttendanceRepository attendanceRepository;
    private final AttendanceValidation attendanceValidation;


    @Override
    public List<AttendanceDto> findAll() {
        return attendanceConverter.convertToDtoList(attendanceRepository.findAll());
    }

    @Override
    @Transactional
    public RestResponseDto save(@NonNull  AttendanceDto attendanceDto) {


        AttendanceError error = attendanceValidation.validateOnSave(attendanceDto);

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

                PersistentAttendanceEntity attendance = attendanceRepository.findById(attendanceDto.getId()).orElse(null);
                if (attendance == null) {
                    return RestResponseDto.INSTANCE()
                            .notFound().message("attendance not found").detail(attendanceDto);
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
}
