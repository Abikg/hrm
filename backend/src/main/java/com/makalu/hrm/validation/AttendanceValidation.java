package com.makalu.hrm.validation;




import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.model.AttendanceDto;
import com.makalu.hrm.repository.AttendanceRepository;
import com.makalu.hrm.repository.UserRepository;
import com.makalu.hrm.utils.AuthenticationUtils;
import com.makalu.hrm.validation.error.AttendanceError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AttendanceValidation {

    private final AttendanceError attendanceError=new AttendanceError();
    private  final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    public AttendanceError validateOnSave(AttendanceDto attendanceDto){
        boolean isValid =validateIp(attendanceDto.getPunchInIp());



        attendanceError.setValid(isValid);
        return attendanceError;
    }



    public AttendanceError validateOnUpdate(AttendanceDto attendanceDto){
        boolean isValid =validateIp(attendanceDto.getPunchOutIp());
          attendanceError.setValid(isValid);
        return attendanceError;
    }



    private boolean validateIp(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            attendanceError.setTitle("punch  ip-adress is needed");
            return false;
        }
        return true;
    }

    public boolean isUserPunchedInToday(){


   Optional<PersistentAttendanceEntity> attendance=attendanceRepository
     .findByCreatedDateAndUser(new Date(),userRepository.findByUsername
                     (AuthenticationUtils.getCurrentUser().getUsername())
                     .get());
   if(attendance.isPresent())
       return true;

   return  false;




    }



}
