package com.book.model;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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

    private String empImageName;

    private List<PositionDTO> positionDTO;

    private List<DepartmentDTO> departmentDTO;

    private List<UUID> positionIdList;

    private List<UUID> departmentIdList;

    private UserDTO userDTO;

}
