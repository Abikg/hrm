package com.makalu.hrm.model;

import com.makalu.hrm.enumconstant.MeetingType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder

public class MeetingMinutesDto {
    private UUID id;

    private String title;
    private Date meetingDate;
    private String minutes;
    private MeetingType meetingType;
    private List<UserDTO> attendedBy;
    private UserDTO createdBy;
    private List<UUID> attendedByIdsFromView;

}
