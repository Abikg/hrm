package com.makalu.hrm.model;
import com.makalu.hrm.converter.DepartmentConverter;
import com.makalu.hrm.converter.EmployeeImageConverter;
import com.makalu.hrm.converter.PositionConverter;
import com.makalu.hrm.converter.UserConverter;
import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.domain.PersistentEmployeeImageEntity;
import com.makalu.hrm.domain.PersistentPositionEntity;
import com.makalu.hrm.domain.PersistentUserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeDTO {


    private UUID id;

    private String fullname;

    private String address;

    private String phone;

    private String email;

    private MultipartFile empImage;

    private boolean createUser = false;

    private UserDTO user;

    private EmployeeImageDTO employeeImage;

    private UUID positionId;

    private UUID departmentId;

    private UUID userId;

    private UUID employeeImageId;

    private ContactDetailDTO contactDetailDTO = new ContactDetailDTO();

    private PersonalDetailDTO personalDetailDTO = new PersonalDetailDTO();

    private List<WorkExperienceDTO> workExperienceDTO = new ArrayList<>();

    public EmployeeDTO() {
        this.workExperienceDTO.add(new WorkExperienceDTO());
    }
}
