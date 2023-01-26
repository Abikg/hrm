package com.makalu.hrm.service.impl;


import com.makalu.hrm.Specification.AttendanceSpecification;
import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.exceptions.AttendanceException;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.model.RestResponseDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.service.AttendanceService;
import com.makalu.hrm.service.UserService;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.utils.DateUtils;
import com.makalu.hrm.validation.AttendanceValidation;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final AttendanceValidation attendanceValidation;
    private final int pageSize=3;


    @Override
    public RestResponseDto Filter(AttendanceDto attendanceDto){
        Boolean noDateFilterFlag=true;


        try {
            if (attendanceDto.getToDate() != null && attendanceDto.getFromDate() != null) {
                AttendanceError error = attendanceValidation.validateDateRange(attendanceDto);

                if (!error.isValid()) {
                    return RestResponseDto.INSTANCE().validationError().detail(Map.of("error", error, "data", attendanceDto));
                }
                noDateFilterFlag = false;
            }

            Page<PersistentAttendanceEntity> pages =  attendanceRepository.findAll(new AttendanceSpecification(attendanceDto),PageRequest.of(attendanceDto.getPage(),pageSize,Sort.by(Sort.Direction.DESC, "punchInDate")));
            return RestResponseDto.INSTANCE().success().detail(Map.of("data", pages, "currentPage", attendanceDto.getPage(),
                    "totalPages", pages.getTotalPages(), "noDateFilterFlag", noDateFilterFlag));

        }catch (Exception ex){
            return  RestResponseDto.INSTANCE().internalServerError().detail(Map.of("date",attendanceDto));
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
    public void punchOut(String ip) {
        PersistentAttendanceEntity previousAttendance = getEntityForPunchOut(AuthenticationUtils.getCurrentUser().getUserId());
        previousAttendance.setPunchOutIp(ip);
        previousAttendance.setPunchOutDate(new Date());
        previousAttendance.setTotalWorkedHours(DateUtils.getHours(previousAttendance.getPunchOutDate(), previousAttendance.getPunchInDate()));
        attendanceRepository.saveAndFlush(previousAttendance);
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
    @Override
    public boolean isValidToPunchOut(UUID userId) {
        try {
            getEntityForPunchOut(userId);
        } catch (AttendanceException ex) {
            return false;
        }
        return true;
    }
    private String getUserName(UUID userid){

        String userName=userService.findById(userid).getUsername();
        if(userName!=null)
          return  userName;

        return  "name not found";

    }
}
