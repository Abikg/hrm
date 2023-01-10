package com.book.validation.error;

import com.book.model.DepartmentDTO;
import com.book.model.PositionDTO;
import com.book.model.UserDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EmployeeError {

    private boolean valid;

    private String fullname;

    private String address;

    private String phone;

    private String email;

    private String empImage;

    private String position;

    private String department;

}
