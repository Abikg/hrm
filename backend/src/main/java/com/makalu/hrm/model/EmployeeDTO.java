package com.makalu.hrm.model;

import com.makalu.hrm.enumconstant.EmployeeStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeDTO {


    private UUID id;

    private String employeeId;

    private long entityEmployeeId;

    private EmployeeStatus employeeStatus;

    private String fullname;

    private boolean isManager = false;

    private String email;

    private Date joinDate;

    private String resignationReason;

    private Date resignationDate;

    private Date exitDate;

    private MultipartFile empImage;

    private boolean createUser = false;

    private UUID approvedById;

    private EmployeeImageDTO employeeImage;

    private UUID positionId;

    private UUID departmentId;

    private UUID userId;

    private UUID employeeImageId;

    private String positionName;

    private String departmentName;

    private ContactDetailDTO contactDetailDTO = new ContactDetailDTO();

    private PersonalDetailDTO personalDetailDTO = new PersonalDetailDTO();

    private List<WorkExperienceDTO> workExperienceDTO = new ArrayList<>();

    private EmployeeDTO manager;

    public EmployeeDTO() {
        this.workExperienceDTO.add(new WorkExperienceDTO());
    }

}
