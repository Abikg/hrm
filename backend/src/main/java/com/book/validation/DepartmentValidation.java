package com.book.validation;

import com.book.domain.PersistentDepartmentEntity;
import com.book.model.DepartmentDTO;
import com.book.repository.DepartmentRepository;
import com.book.validation.error.DepartmentError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DepartmentValidation {

    private final DepartmentRepository departmentRepository;
    private final DepartmentError error = new DepartmentError();

    public DepartmentError validateOnSave(DepartmentDTO dto){
        boolean isValid = validateTitle(dto.getTitle());
        isValid = isValid & validateCode(dto.getDepartmentCode());
        isValid = isValid & validateDetails(dto.getDetail());
        isValid = isValid && validateUnique(dto.getTitle().trim(), dto.getDepartmentCode().trim());

        error.setValid(isValid);
        return error;
    }

    public DepartmentError validateOnUpdate(DepartmentDTO dto){
        boolean isValid = validateTitle(dto.getTitle());
        isValid = isValid & validateCode(dto.getDepartmentCode());
        isValid = isValid & validateDetails(dto.getDetail());
        //todo validate unique constraints

        error.setValid(isValid);
        return error;
    }

    @Transactional
    boolean validateUnique(String title, String code){
        PersistentDepartmentEntity departmentEntity = departmentRepository.findByTitleOrDepartmentCode(title, code);

        if (departmentEntity == null){
            return true;
        }

        if (departmentEntity.getTitle().equals(title)){
            error.setTitle("Please provide the unique title");
        }

        if (departmentEntity.getDepartmentCode().equals(code)){
            error.setDepartmentCode("Please provide the unique code");
        }

        return false;
    }

    private boolean validateTitle(String title){
        if (title == null || title.trim().isEmpty()){
            error.setTitle("Title is required");
            return false;
        }

        if (title.length() > 51){
            error.setTitle("Title must be less than 50 characters");
            return false;
        }

        return true;
    }

    private boolean validateCode(String code){
        if (code == null || code.trim().isEmpty()){
            error.setDepartmentCode("Code is required");
            return false;
        }

        if (code.length() > 10){
            error.setDepartmentCode( "Code must be less than 10 characters");
            return false;
        }

        return true;
    }

    private boolean validateDetails(String details){
        if (details == null || details.trim().isEmpty()){
            error.setDetail("Details is required");
            return false;
        }

        if (details.length() > 500){
            error.setDetail("Details must be less than 500 characters");
            return false;
        }

        return true;
    }
}
