package com.makalu.hrm.validation;

import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.model.MeetingMinutesDto;
import com.makalu.hrm.validation.error.DepartmentError;
import com.makalu.hrm.validation.error.MeetingMinuteError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class MeetingMinuteValidation {

    private final MeetingMinuteError error = new MeetingMinuteError();

    public MeetingMinuteError validateOnSave(MeetingMinutesDto dto) {
        boolean isValid = validateTitle(dto.getTitle());
        isValid = isValid & validateDate(dto.getMeetingDate());
        isValid = isValid && validateMinute(dto.getMinutes());

        error.setValid(isValid);
        return error;
    }

    private boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            error.setTitle("Title is required");
            return false;
        }

        if (title.length() > 200) {
            error.setTitle("Title must be less than 200 characters");
            return false;
        }

        return true;
    }

    public boolean validateMinute(String minute) {

        if (minute == null || minute.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean validateDate(Date d) {
        if (d.toString().isEmpty()) {
            return false;
        }
        return true;


    }


}
