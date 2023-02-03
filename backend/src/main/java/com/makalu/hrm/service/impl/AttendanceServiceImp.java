package com.makalu.hrm.service.impl;


import com.makalu.hrm.Specification.AttendanceSpecification;
import com.makalu.hrm.constant.NumericConstant;
import com.makalu.hrm.converter.AttendanceConverter;
import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.exceptions.AttendanceException;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.DateUtils;
import com.makalu.hrm.utils.FieldService;
import com.makalu.hrm.validation.AttendanceValidation;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final AttendanceValidation attendanceValidation;
    private final FieldService fieldService;
    private final AttendanceConverter attendanceConverter;

    @Override
    public RestResponseDto filter(AttendanceDto attendanceDto) {

        try {
            if (attendanceDto.getToDate() != null && attendanceDto.getFromDate() != null) {
                AttendanceError error = attendanceValidation.validateDateRange(attendanceDto);
                if (!error.isValid()) {
                    return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", attendanceDto));
                }
            }
            List<AttendanceDto> list = attendanceConverter.convertToDtoList(attendanceRepository.findAll(new AttendanceSpecification(attendanceDto)));
            return RestResponseDto.INSTANCE().success().detail(list).column(fieldService.getAttendanceFields());

        } catch (Exception ex) {
            return RestResponseDto.INSTANCE().internalServerError().detail(Map.of("data", attendanceDto));
        }
    }

    @Transactional
    @Override
    public void punchIn(String ip) {
        if (!isValidToPunchIn(AuthenticationUtils.getCurrentUser().getUserId())) {
            throw new AttendanceException("This user is no valid for punch in" + AuthenticationUtils.getCurrentUser().getUsername());
        }
        PersistentAttendanceEntity entity = new PersistentAttendanceEntity();
        entity.setPunchInIp(ip);
        entity.setUser(userService.getCurrentUserEntity());
        attendanceRepository.saveAndFlush(entity);

    }

    @Transactional
    @Override
    public RestResponseDto punchOut(String ip) {
        PersistentAttendanceEntity previousAttendance = getEntityForPunchOut(AuthenticationUtils.getCurrentUser().getUserId());
        Date today = new Date();
        double hours = DateUtils.getHours(today, previousAttendance.getPunchInDate());
        if (hours < NumericConstant.OFFICE_HOURS) {
            previousAttendance.setPunchOutIp(ip);
            previousAttendance.setPunchOutDate(today);
            previousAttendance.setTotalWorkedHours(hours);
            attendanceRepository.saveAndFlush(previousAttendance);
            return RestResponseDto.INSTANCE().success().detail(Map.of("dayPassed", false));
        } else {

            return RestResponseDto.INSTANCE().success().detail(Map.of("dayPassed", true));
        }

    }

    private PersistentAttendanceEntity getLastAttendanceOfUser(UUID userId) {
        List<PersistentAttendanceEntity> attendanceEntityList = attendanceRepository.findAllByUser_Id(userId, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "punchInDate")));
        if (attendanceEntityList == null || attendanceEntityList.isEmpty()) {
            return null;
        }
        return attendanceEntityList.get(0);
    }

    private PersistentAttendanceEntity getEntityForPunchOut(UUID userId) {
        PersistentAttendanceEntity previousAttendance = getLastAttendanceOfUser(AuthenticationUtils.getCurrentUser().getUserId());
        if (previousAttendance == null) {
            throw new AttendanceException("There is no previous punch in record for this user " + AuthenticationUtils.getCurrentUser().getUsername());
        }

        if (previousAttendance.getPunchOutDate() == null || DateUtils.hasSameDay(previousAttendance.getPunchInDate(), new Date())) {
            return previousAttendance;

        }

        throw new AttendanceException("This user has already did the punch out " + AuthenticationUtils.getCurrentUser().getUsername());
    }

    @Override
    public boolean isValidToPunchIn(UUID userId) {
        PersistentAttendanceEntity previousAttendance = getLastAttendanceOfUser(AuthenticationUtils.getCurrentUser().getUserId());
        if (previousAttendance == null) {
            return true;
        }
        if (previousAttendance.getPunchOutDate() != null && !DateUtils.hasSameDay(previousAttendance.getPunchInDate(), new Date())) {
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public RestResponseDto setPunchinAnotherDay(String time, String ips) {
        PersistentAttendanceEntity previousAttendance = getEntityForPunchOut(AuthenticationUtils.getCurrentUser().getUserId());
        int hours = Integer.parseInt(time.split(":")[0]);
        if (hours < NumericConstant.OFFICE_START || hours > NumericConstant.OFFICE_END) {
            return RestResponseDto.INSTANCE().success().detail(Map.of("notOfficeHours", true));
        } else {
            previousAttendance.setPunchOutDate(getPreviousPunchoutDate(time, previousAttendance.getPunchInDate()));
            double workHours = DateUtils.getHours(previousAttendance.getPunchOutDate(), previousAttendance.getPunchInDate());
            previousAttendance.setPunchOutIp(ips);
            previousAttendance.setTotalWorkedHours(workHours);
            attendanceRepository.saveAndFlush(previousAttendance);
            return RestResponseDto.INSTANCE().detail(Map.of("notOfficeHours", false));
        }

    }

    private Date getPreviousPunchoutDate(String time, Date punchindate) {
        Date tempDate = new Date();
        tempDate.setYear(punchindate.getYear());
        tempDate.setMonth(punchindate.getMonth());
        tempDate.setDate(punchindate.getDate());
        tempDate.setHours(Integer.parseInt(time.split(":")[0]));
        tempDate.setMinutes(Integer.parseInt(time.split(":")[1]));
        tempDate.setSeconds(Integer.parseInt("00"));
        return tempDate;

    }

    @Override
    public boolean isValidToPunchOut(UUID userId) {
        try {
            getEntityForPunchOut(userId);
        } catch (AttendanceException ex) {
            return false;
        }
        return true;
    }

    private String getUserName(UUID userid) {

        String userName = userService.findById(userid).getUsername();
        if (userName != null)
            return userName;

        return "name not found";

    }
}
