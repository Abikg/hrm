package com.makalu.hrm.validation;

import com.makalu.hrm.domain.PersistentDepartmentEntity;
import com.makalu.hrm.model.DepartmentDTO;
import com.makalu.hrm.repository.DepartmentRepository;
import com.makalu.hrm.validation.error.DepartmentError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentValidation {

    private final DepartmentRepository departmentRepository;
    private static DepartmentError error;

    public DepartmentError validateOnSave(DepartmentDTO dto) {
        error = new DepartmentError();
        boolean isValid = validateTitle(dto.getTitle());
        isValid = isValid & validateCode(dto.getDepartmentCode());
        isValid = isValid & validateDetails(dto.getDetail());
        isValid = isValid && validateUnique(dto.getTitle().trim(), dto.getDepartmentCode().trim());

        error.setValid(isValid);
        return error;
    }

    public DepartmentError validateOnUpdate(DepartmentDTO dto) {
        error = new DepartmentError();
        boolean isValid = validateTitle(dto.getTitle());
        isValid = isValid & validateCode(dto.getDepartmentCode());
        isValid = isValid & validateDetails(dto.getDetail());
        //todo validate unique constraints
        isValid = isValid && validateUniqueOnUpdate(dto);
        error.setValid(isValid);
        return error;
    }

    @Transactional
    boolean validateUnique(String title, String code) {
        List<PersistentDepartmentEntity> departmentEntityList = departmentRepository.findByTitleOrDepartmentCode(title, code);

        if (departmentEntityList.size() == 0) {
            return true;
        }

        if (departmentEntityList.stream().anyMatch(d->d.getTitle().equals(title))) {
            error.setTitle("Please provide the unique title");

        }

        if (departmentEntityList.stream().anyMatch(d->d.getDepartmentCode().equals(code))) {
            error.setDepartmentCode("Please provide the unique code");
        }

        return false;
    }

    @Transactional
    boolean validateUniqueOnUpdate(DepartmentDTO dto){
        List<PersistentDepartmentEntity> departmentEntityList = departmentRepository.findByTitleOrDepartmentCode(dto.getTitle(), dto.getDepartmentCode());
        if (departmentEntityList.size() > 0){
            return true;
        }
        if(departmentEntityList.stream().anyMatch(d->(d.getTitle().equals(dto.getTitle()) && !d.getId().equals(dto.getId())))){
            error.setTitle("Please provide the unique title");
            return false;
        }
        if(departmentEntityList.stream().anyMatch(d->(d.getDepartmentCode().equals(dto.getDepartmentCode()) && !d.getId().equals(dto.getId())))){
            error.setDepartmentCode("Please provide the unique title");
            return false;
        }

        return true;
    }

    private boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            error.setTitle("Title is required");
            return false;
        }

        if (title.length() > 51) {
            error.setTitle("Title must be less than 50 characters");
            return false;
        }

        return true;
    }

    private boolean validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            error.setDepartmentCode("Code is required");
            return false;
        }

        if (code.length() > 10) {
            error.setDepartmentCode("Code must be less than 10 characters");
            return false;
        }

        return true;
    }

    private boolean validateDetails(String details) {
        if (details == null || details.trim().isEmpty()) {
            error.setDetail("Details is required");
            return false;
        }

        if (details.length() > 500) {
            error.setDetail("Details must be less than 500 characters");
            return false;
        }

        return true;
    }
}
