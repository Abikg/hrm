package com.book.validation;

import com.book.domain.PersistentEmployeeEntity;
import com.book.model.DepartmentDTO;
import com.book.model.EmployeeDTO;
import com.book.model.PositionDTO;
import com.book.repository.EmployeeRepository;
import com.book.validation.error.EmployeeError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class EmployeeValidation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final int MAX_FILE_SIZE = 3145728;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg");


    private final EmployeeRepository employeeRepository;
    private final EmployeeError error = new EmployeeError();

    public EmployeeError validateOnSave(EmployeeDTO dto) {
        boolean isValid = validateFullName(dto.getFullname());
        isValid = isValid & validateEmail(dto.getEmail());
        isValid = isValid & validatePhone(dto.getPhone());
        isValid = isValid & validateAddress(dto.getAddress());
        isValid = isValid & validateImage(dto.getEmpImage());
        isValid = isValid & validatePosition(dto.getPositionDTO());
        isValid = isValid & validateDepartment(dto.getDepartmentDTO());
        isValid = isValid && validateUnique(dto.getPhone().trim(), dto.getEmail().trim());
        error.setValid(isValid);
        return error;
    }


    public EmployeeError validateOnUpdate(EmployeeDTO dto) {
        boolean isValid = validateFullName(dto.getFullname());
        isValid = isValid & validateEmail(dto.getEmail());
        isValid = isValid & validatePhone(dto.getPhone());
        isValid = isValid & validateAddress(dto.getAddress());
        isValid = isValid & validatePosition(dto.getPositionDTO());
        isValid = isValid & validateDepartment(dto.getDepartmentDTO());
        isValid = isValid & validateImage(dto.getEmpImage());
        error.setValid(isValid);
        return error;
    }

    @Transactional
    boolean validateUnique(String phone, String email) {
        PersistentEmployeeEntity employeeEntity = employeeRepository.findByPhoneOrEmail(phone, email);

        if (employeeEntity == null) {
            return true;
        }

        if (employeeEntity.getPhone().equals(phone)) {
            error.setPhone("Please provide a unique phone number");
        }

        if (employeeEntity.getEmail().equals(email)) {
            error.setEmail("Please provide a unique email address");
        }

        return false;
    }

    private boolean validateFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            error.setFullname("Name is required");
            return false;
        }

        if (name.length() > 50) {
            error.setFullname("Name must be less than 50 characters");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            error.setEmail("Email is required");
            return false;
        }else {
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            if (!matcher.matches()) {
                error.setEmail("Incorrect email format");
                return false;
            }
        }
        return true;
    }

    private boolean validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            error.setPhone("Phone number is required");
            return false;
        }
        if (phone.length() != 10) {
            error.setPhone("Phone number must have 10 digit");
            return false;
        }
        return true;
    }

    private boolean validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            error.setAddress("Address number is required");
            return false;
        }
        if (address.length() > 50) {
            error.setAddress("Address can't have more than 50 characters");
            return false;
        }
        return true;
    }

    private boolean validateImage(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            if (file.getSize() > MAX_FILE_SIZE) {
                error.setEmpImage("Image must be less than 3MB");
                return false;
            }

            if (!ALLOWED_EXTENSIONS.contains(ext)) {
                error.setEmpImage("Invalid file. Supports: jpg,jpeg,png only");
                return false;
            }
        }
        return true;
    }

    private boolean validatePosition(List<PositionDTO> dtos){
        if(dtos == null){
            error.setPosition("Position is required");
            return false;
        }
        return true;
    }

    private boolean validateDepartment(List<DepartmentDTO> dtos){
        if(dtos == null){
            error.setDepartment("Department is required");
            return false;
        }
        return true;
    }
}