package com.makalu.hrm.model;

import com.makalu.hrm.enumconstant.EmployeeStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeDTO {


    private UUID id;

    private String employeeId;

    @Enumerated(EnumType.STRING)
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

    private UUID managerId;

    private String reportingManagerName;

    private List<String> subordinates;

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public EmployeeDTO() {
        this.workExperienceDTO.add(new WorkExperienceDTO());
    }

}
